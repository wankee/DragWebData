import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class Test {
	private static boolean TESTS = true;

	/*
	 * BUG：第一章变成第第一章回 BUG：第第一回 纵横钩党清流祸回 峭茜风期月旦评 第跟回加错位置
	 */
	public static void main(String[] args) {

		/*String name = "I am 君山";
		char[] ch=name.toCharArray();
		//toHex(name.toCharArray());
		 System.out.println(name.toCharArray());
		// System.out.println((Integer)name.getBytes());
		 byte[] b=name.getBytes();
		 for (int i = 0; i < b.length; i++) {  
			 
		     String hex = Integer.toHexString(b[i] & 0xFF);    
		     if (hex.length() == 1) {   
		    	    hex = '0' + hex;   
		    	   }   
		     System.out.println(hex +"***");
		     if (hex.length() == 1) {    
		       hex = '0' + hex;    
		     }    
		     System.out.print(hex.toUpperCase() );
		 }*/
		// String address="http://www.365essay.com/jinyongquanji/02/INDEX.HTM";
		//String address = "http://www.365essay.com/jinyongquanji/02/INDEX.HTM";
		String address = "http://vignette4.wikia.nocookie.net/asoiaf/images/2/24/Map_of_westeros_CN.jpg/revision/latest?cb=20120304080614&path-prefix=zh";
		
		BufferedInputStream in;
		try {
			in = new BufferedInputStream(new URL(address).openStream());
			File rootDir = new File(
					"C:\\Users\\ann.chen\\Desktop\\download\\novels_orig");
			if (!rootDir.exists()) {
				rootDir.mkdirs();
			}
			FileOutputStream fot = new FileOutputStream(rootDir+"\\test.jpg");
			byte[] buffer=new byte[1024];
			int len;
			long startTime=System.currentTimeMillis();
			System.out.println(new Date().toLocaleString());
			
			
			while ((len=in.read(buffer)) != -1) {
				//fot.wr
				fot.write(buffer,0,len);
				//fot.flush();
			}
			fot.flush();
			in.close();
			fot.close();
			long endTime=System.currentTimeMillis();
			float elapseTime=(float) ((endTime-startTime)/1000.0);
			System.out.println(elapseTime);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// htmlProcess.getText("http://www.365essay.com/jinyongquanji/02/002/INDEX.HTM");
		// htmlProcess.getChapters("http://www.365essay.com/jinyongquanji/02/005/INDEX.HTM");
		// new Book("http://www.365essay.com/jinyongquanji/02/IMAGES/04.JPG",
		// "IMAGES/03.JPG").getCover();

		// String str="<HTML><TITLE>二</TITLE><HEAD><META ";
		// System.out.println("text====>"+str.replaceAll(".*<TITLE>(.*)</TITLE>.*",
		// "$1"));
		// String
		// outstr=str.replaceAll("<TITLE>(第[一二三四五六七八九十]{1,3}[回章]) (.*)</TITLE>",
		// "第$1回 $2");
		// String
		// outstr=str.replaceAll("<TITLE>([一二三四五六七八九十]{1,3})(( .*)|)</TITLE>",
		// "第$1回$2");
		// System.out.println(outstr);

		if (!TESTS) {
			HtmlProcess htmlProcess = new HtmlProcess();

			File rootDir = new File(
					"C:\\Users\\ann.chen\\Desktop\\download\\novels_orig");
			if (!rootDir.exists()) {
				rootDir.mkdirs();
			}
			//Date date=new Date();
			//date.getTime();
			
			
			long startTime=System.currentTimeMillis();
			System.out.println(new Date().toLocaleString());
			List<Book> books = htmlProcess.getBooks(address);
			for (Book book : books) {
				book.generate(rootDir);
			}
			System.out.println(new Date().toLocaleString());
			long endTime=System.currentTimeMillis();
			float elapseTime=(float) ((endTime-startTime)/1000.0);
			System.out.println(elapseTime);
		}
	}
}
