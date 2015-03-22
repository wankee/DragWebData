import java.io.File;
import java.util.List;

public class Test {

	/*
	 * BUG：第一章变成第第一章回
	 * BUG：第第一回 纵横钩党清流祸回 峭茜风期月旦评 第跟回加错位置
	 */
	public static void main(String[] args) {
		String address="http://www.365essay.com/jinyongquanji/02/INDEX.HTM";
		//URL url=new URL(address);
		HtmlProcess htmlProcess = new HtmlProcess();
		//htmlProcess.getText("http://www.365essay.com/jinyongquanji/02/002/INDEX.HTM");
		//htmlProcess.getChapters("http://www.365essay.com/jinyongquanji/02/005/INDEX.HTM");
		//new Book("http://www.365essay.com/jinyongquanji/02/IMAGES/04.JPG", "IMAGES/03.JPG").getCover();
		
		File rootDir=new File("C:\\Users\\ann.chen\\Desktop\\download\\novels");
		if(!rootDir.exists()) {
			rootDir.mkdirs();
		}
		
		//String str="<TITLE>二</TITLE>";
		//String outstr=str.replaceAll("<TITLE>(第[一二三四五六七八九十]{1,3}[回章]) (.*)</TITLE>", "第$1回 $2");
		//String outstr=str.replaceAll("<TITLE>([一二三四五六七八九十]{1,3})(( .*)|)</TITLE>", "第$1回$2");
		//System.out.println(outstr);
		List<Book> books=htmlProcess.getBooks(address);
		for (Book book:books){
			book.generate(rootDir);
		}
	}
}
