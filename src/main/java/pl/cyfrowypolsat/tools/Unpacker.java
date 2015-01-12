package pl.cyfrowypolsat.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.naming.InvalidNameException;

import org.apache.commons.io.FilenameUtils;

public class Unpacker {
	
	public File unpack(File file) throws FileNotFoundException, IOException, InvalidNameException{
		File unpackedFile;
		String ext = FilenameUtils.getExtension(file.getAbsolutePath());
		if(ext.equals("zip")){
			unpackedFile = unpackZip(file);
		}else if(ext.equals("gz")){
			unpackedFile = unpackGZip(file);
		}else{
			throw new InvalidNameException("Unknown file extension");
		}
		return unpackedFile;
	}
	
	public static String getUnpackedFileName(File file){
		if(FilenameUtils.getExtension(file.getAbsolutePath()).equals("zip") || FilenameUtils.getExtension(file.getAbsolutePath()).equals("gz")){
			return file.getAbsolutePath().replaceAll(".[a-z]+$", "");
		}
		return file.getAbsolutePath();
	}

	private File unpackZip(File file) throws FileNotFoundException, IOException{
		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
		ZipEntry ze = zis.getNextEntry();
		File newFile = null;
		while(ze != null){
			String fileName = ze.getName();
			newFile = new File(file.getParent() + File.separator + fileName);
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while((len = zis.read(buffer)) > 0){
				fos.write(buffer, 0, len);
			}
			fos.close();
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		return newFile;
	}
	
	private File unpackGZip(File file) throws FileNotFoundException, IOException{
		File outputFile = new File(getUnpackedFileName(file));
		byte[] buffer = new byte[1024];
		GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(file));
		FileOutputStream out = new FileOutputStream(outputFile);
		int len;
		while((len = gzis.read(buffer)) > 0){
			out.write(buffer, 0, len);
		}
		gzis.close();
		out.close();
		return outputFile;
	}
}
