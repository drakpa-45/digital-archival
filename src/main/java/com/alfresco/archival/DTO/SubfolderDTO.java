package com.alfresco.archival.DTO;

import java.util.List;


public class SubfolderDTO {
	private String name;
    private List<String> files;
    private List<SubfolderDTO> subfolders;
    
 // Getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	public List<SubfolderDTO> getSubfolders() {
		return subfolders;
	}
	public void setSubfolders(List<SubfolderDTO> subfolders) {
		this.subfolders = subfolders;
	}
}
