package com.limon.base.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class FileInfo {
	private String fileName;
	private String fileStoreName;
	private String filePath;
	private String fileType;
	private String fileSize;//单位M
	private long fileLongSize;//文件大小单位字节
	private String contentType;
	private Date uploadDate;
	private Integer uploadResult;
	private String failReason;
	private MultipartFile mf;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileStoreName() {
		return fileStoreName;
	}
	public void setFileStoreName(String fileStoreName) {
		this.fileStoreName = fileStoreName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	/**
	 * 上传结果 1成功 0失败
	 * @return
	 */
	public Integer getUploadResult() {
		return uploadResult;
	}
	/**
	 * 上传结果 1成功 0失败
	 * @return
	 */
	public void setUploadResult(Integer uploadResult) {
		this.uploadResult = uploadResult;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public long getFileLongSize() {
		return fileLongSize;
	}
	public void setFileLongSize(long fileLongSize) {
		this.fileLongSize = fileLongSize;
	}
	public MultipartFile getMf() {
		return mf;
	}
	public void setMf(MultipartFile mf) {
		this.mf = mf;
	}
}
