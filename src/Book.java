import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Book {

	private String mHref;
	private String mImgUrl;
	private HtmlProcess mHtmlProcess;
	private String mImgName;

	public Book(String url, String href, String img) {
		this.mHref = url.replaceAll("INDEX.HTM", href);
		this.mImgUrl = url.replaceAll("INDEX.HTM", img);
		mImgName=img.replaceAll("IMAGES/(.*)", "$1");
		mHtmlProcess=new HtmlProcess();
		//System.out.println("href:***" + href);
		//System.out.println("img:***" + img);
	}
	
	public String getText() {
		String str=mHtmlProcess.getText(mHref);
		//if (str.equals("HAS_CAPTTERS!")) {
		//	return mHtmlProcess.getText(mHref);
		//}
		return str;
	}
	
	public void generateCover(String path) {
		File file = new File(path + File.separator + mImgName);
		//System.out.println(path + File.separator + mImgName);
		mHtmlProcess.getImage(mImgUrl, file);
	}

	public void generate(File rootDir) {
		// TODO Auto-generated method stub
		String bookDir=rootDir.getPath() + File.separator + getBookName();
		//System.out.println();
		new File(bookDir).mkdirs();
		generateCover(bookDir);
		generateContent(bookDir);
	}
	private void generateContent(String bookDir) {
		// TODO Auto-generated method stub
		File file = new File(bookDir+ File.separator+getBookName()+".txt");
		try {
			new FileWriter(file).write("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writeToFile(file, getText());
	}
	
	private void writeToFile(File file, String str) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getBookName(){
		//System.out.println(mHref);
		return mHtmlProcess.getTitle(mHref);
	}
}
