import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlProcess {

	private static int TOTAL_CHAPTERS = 41;

	private String mContent;

	/*public HtmlProcess() {
		//this.mUrl = url;
	}*/

	public List<Book> getBooks(String url) {
		getHtmlContent(url);
		String regex;
		List<Book> books = new ArrayList<Book>();
		regex = "<a href=([^>]*)><img src=([^>]*) border[^>]*></a>";
		// regex = "<a href=([^\\s>]*)>.*?</a>";
		Pattern pa = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher ma = pa.matcher(mContent);
		while (ma.find()) {
			String substr = ma.group();

			String href = substr.replaceAll("<A HREF=([^>]*)>.*", "$1");
			String img = substr.replaceAll(".*<IMG SRC=([^>]*) border.*", "$1");
			books.add(new Book(url, href, img));
			// System.out.println("href:***" + href);
			// System.out.println("img:***" + img);
		}
		System.out.println(books.size());
		return books;
	}

	public List<String> generateUrls() {
		// TODO Auto-generated method stub
		List<String> urls = new ArrayList<String>();
		for (int i = 1; i <= TOTAL_CHAPTERS; i++) {
			urls.add("http://www.365essay.com/jinyongquanji/02/012/"
					+ String.format("%03d", i) + ".htm");
			// System.out.println("abc****" + urls);
		}
		return urls;
	}

	/**
	 * 读取网页全部内容
	 */
	public HtmlProcess getHtmlContent(String address) {
		String temp;
		StringBuffer sb = new StringBuffer();
		
		
		
		
		
		HttpURLConnection connection = null;
		
		try {
			URL url = new URL(address);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(30 * 1000); // 设置最长连接超时时间，以免进程卡死
			connection.setReadTimeout(60 * 1000); // 设置最长读取时间，以免进程卡死
			// 以下三行主要是为了模拟网页访问，在拔取网页内容时不易被发现
			HttpURLConnection.setFollowRedirects(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-type", "application/x-java-serialized-object"); 
			//BufferedInputStream bis = null;

			/*bis = new BufferedInputStream(connection.getInputStream());
			int size;
			while ((size = bis.read(buffer)) != &minus;1) {
				sb.append(size);
			}*/
			//connection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));// 读取网页全部内容
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}
			in.close();
		} catch (final MalformedURLException me) {
			System.out.println("你输入的URL格式有问题!");
			me.getMessage();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.mContent=sb.toString();
		return this;
	}

	public boolean hasChapters() {
		return mContent.matches(".*<P [^>]*><A [^>]*>.*");
	}
	
	public List<String> getChapters() {
		//getHtmlContent(url);
		String regex;
		List<String> chapters = new ArrayList<String>();
		regex = "<a href=[^>]*>[^>]*</a>";
		// regex = "<a href=([^\\s>]*)>.*?</a>";
		Pattern pa = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher ma = pa.matcher(mContent);
		while (ma.find()) {
			String substr = ma.group();
			String href = substr.replaceAll("<A HREF=([^>]*)>.*", "$1");
			chapters.add(href);
		}
		//System.out.println("chapters"+chapters);
		return chapters;
	}
	
	public String getText(String url) {
		// TODO Auto-generated method stub
		// content = content.replaceAll("<p><em>.*?</em></p>", "");// 去图片注释
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getHtmlContent(url);
		//System.out.println("===url===="+mContent);
		//mContent="N=LEFT><P STYLE=LINE-HEIGHT: 150%><BR>　　嗖";
		String text="";
		if (!hasChapters()) {
			//System.out.println("===ifff====");
			text = mContent
					.replaceAll("<HEAD>.*</HEAD>", "")
					.replaceAll("<DIV.*>.*</DIV>", "")
					.replaceAll("<FONT .*>.*</FONT>", "")
					.replaceAll("<BR><BR>", "\n").replaceAll("<BR>", "\n");
			System.out.println("text====>"+text);
			if (!mContent.matches("<TITLE>第[一二三四五六七八九十]{1,3}[回章] (.*)</TITLE>*")) {
				return outTag(text.replaceAll("<TITLE>([一二三四五六七八九十]{1,3})(( .*)|)</TITLE>", "第$1回$2"));
			}
			return outTag(text);
		} else {
			//System.out.println("===else====");
			StringBuffer content = new StringBuffer();
			for (String str:getChapters()) {
				content.append(getText(url.replaceAll("INDEX.HTM", str)));
				//System.out.println(str);
				//System.out.println("text****==>");
			}
			return content.toString();
			//return "HAS_CAPTTERS!";
		}
	}

	public String getTitle(String url) {
		//System.out.println("url*********"+url);
		Pattern pa = Pattern.compile("<title>.*</title>",
				Pattern.CASE_INSENSITIVE);
		Matcher ma = pa.matcher(getHtmlContent(url).mContent);
		String title = "";
		while (ma.find()) {
			title = ma.group();
			//System.out.println("title*********"+title);
		}

		return outTag(title);
	}

	/**
	 * 
	 * @param s
	 * @return 去掉标记
	 */
	public String outTag(final String s) {
		return s.replaceAll("<.*?>", "");
	}

	public void getImage(String url, File file) {
		// TODO Auto-generated method stub
		BufferedInputStream in;
		try {
			in = new BufferedInputStream(new URL(url).openStream());
			FileOutputStream fot = new FileOutputStream(file);
			int t;
			while ((t=in.read()) != -1) {
				fot.write(t);
				fot.flush();
			}
			in.close();
			fot.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
