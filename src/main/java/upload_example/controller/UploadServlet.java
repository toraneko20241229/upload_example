package upload_example.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * アップロードサンプル
 * 
 *  参考URL:
 *  https://stackoverflow.com/questions/2422468/how-can-i-upload-files-to-a-server-using-jsp-servlet
 *  https://stackoverflow.com/questions/18664579/recommended-way-to-save-uploaded-files-in-a-servlet-application
 *
 */
@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String basedirstr = "/static/uploaded/";
	private static final String uploaddirstr = System.getenv("UPLOAD_LOCATION");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("filelist", getImageFileList(getUploadDirPath()));
		request.getRequestDispatcher("/jsp/board.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Part part = request.getPart("file");
		String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		String urlstr = getUploadBaseURI().resolve(getURLEncodedFilename(filename)).getPath();
		String outstr = getUploadDirPath().resolve(filename).toString();
		var outpath = new File(outstr).toPath();
		try (InputStream in = part.getInputStream()) {
			Files.copy(in, outpath); // 上書きしたい場合はStandardCopyOption.REPLACE_EXISTINGを付ける
		}
		request.setAttribute("uploaded_path", outstr);
		request.setAttribute("download_url", urlstr);
		request.setAttribute("filelist", getImageFileList(getUploadDirPath()));
		request.getRequestDispatcher("/jsp/board.jsp").forward(request, response);
	}

	private List<String> getImageFileList(Path dirpath) throws IOException {
		List<String> filelist = Files.walk(dirpath)
				.map(p -> p.toFile())
				.filter(f -> f.isFile())
				.filter(f -> f.getName().matches("(?i)^.*\\.(jpg|jfif|jpeg|png|bmp|webp|gif)$"))
				.map(f -> getUploadBaseURI().resolve(getURLEncodedFilename(f.getName())).toASCIIString())
				.toList();
		return filelist;
	}

	private Path getUploadDirPath() {
		 // uploaddirstr != null時は推奨されない保存先
		String pathstr = uploaddirstr != null ? uploaddirstr : getServletContext().getRealPath(basedirstr);  
		return new File(pathstr).toPath();
	}

	private URI getUploadBaseURI() {
		String urlstr = uploaddirstr != null ? basedirstr : getServletContext().getContextPath() + basedirstr;
		return getURI(urlstr);
	}

	private static String getURLEncodedFilename(String filename) {
		return getFilenameFrom((new File(filename).toURI().toASCIIString()));
	}

	private static String getFilenameFrom(String urlstr) {
		return urlstr.substring(urlstr.lastIndexOf('/') + 1, urlstr.length());
	}

	private static URI getURI(String urlstr) {
		try {
			return new URI(urlstr);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
