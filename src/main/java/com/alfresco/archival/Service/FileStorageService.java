package com.alfresco.archival.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionInvocationTargetException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alfresco.archival.DTO.SubfolderDTO;
import com.alfresco.archival.Entity.DocumentEntityDRC;
import com.alfresco.archival.Entity.DrcFolder;
import com.alfresco.archival.Repository.DocumentRepository;
import com.alfresco.archival.Repository.FolderRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.storage.path}")
    private String storagePath;

    private final DocumentRepository documentRepository;

    public FileStorageService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Autowired
    private FolderRepository folderRepository;
    
    // Initialize storage directory
    public void init() {
        try {
            Path path = Paths.get(storagePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize file storage", e);
        }
    }

    // Store file and save metadata to database
    @Transactional
    public DocumentEntityDRC storeFile(MultipartFile file, String documentType, String type) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + originalFileName; // Unique file name
        Path targetLocation = Paths.get(storagePath).resolve(fileName);

        // Ensure the parent directory exists
        Files.createDirectories(targetLocation.getParent());

        try {
            // Save file to the local storage
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException e) {
            System.err.println("Failed to save file: " + fileName);
            e.printStackTrace();
            throw e;  // Re-throw or handle as needed
        }

        // Save metadata in the database
        DocumentEntityDRC document = new DocumentEntityDRC();
        document.setAlfrescoRefNo(UUID.randomUUID().toString());
        document.setDocument_type(documentType);
        document.setDocument_name(originalFileName);
        document.setUpload_url(targetLocation.toString());
        document.setUuid(uuid);
        document.setType(type);

        return documentRepository.save(document);
    }


    /**
     * Creates a folder inside the base storage path.
     *
     * @param folderName Name of the folder to create
     * @throws IOException If folder creation fails or folder already exists
     */
    public void createFolder(String folderName) throws IOException {
        // Construct the full path for the folder
        String folderPath = storagePath + File.separator + folderName;

        // Create a File object for the folder
        File folder = new File(folderPath);

        // Check if the folder already exists
        if (folder.exists()) {
            throw new IOException("Folder already exists: " + folderPath);
        }

        // Attempt to create the folder
        if (!folder.mkdirs()) {
            throw new IOException("Failed to create folder: " + folderPath);
        }
    }
    
    // Create a subfolder inside an existing folder
    public void createSubFolder(String parentFolderName, String subFolderName) throws IOException {
        File parentFolder = new File(storagePath + File.separator + parentFolderName);

        if (parentFolder.exists() && parentFolder.isDirectory()) {
            File subFolder = new File(parentFolder, subFolderName);
            if (!subFolder.exists()) {
                if (subFolder.mkdirs()) {
                    System.out.println("Subfolder created successfully: " + subFolderName);
                } else {
                    throw new IOException("Failed to create subfolder: " + subFolderName);
                }
            } else {
                throw new IOException("Subfolder already exists: " + subFolderName);
            }
        } else {
            throw new IOException("Parent folder does not exist: " + parentFolderName);
        }
    }
    
    // Create a file (if required)
    public void createFile(String folderPath, String fileName) throws IOException {
        Path path = Paths.get(storagePath, folderPath, fileName);  // Use injected path
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }
    
    @Transactional
    public void createFolderStructure(String parentFolder, List<SubfolderDTO> subfolders, String isInternal) throws IOException {

        // Combine the storage path with the parent folder correctly
        Path parentFolderPath = isInternal.equalsIgnoreCase("No")
                ? Paths.get(storagePath, parentFolder)
                : Paths.get(parentFolder);

        // Create the parent folder if it doesn't exist
        createFolderIfNotExists(parentFolderPath.toString()); 

        // Check if the parent folder already exists in the database, if not, create it
        Optional<DrcFolder> existingParentFolder = folderRepository.findByNameAndParentFolderIdIsNull(parentFolder);
        DrcFolder parentFolderEntity = null;
        if (existingParentFolder.isEmpty()) {
            // Save the parent folder in the database
            parentFolderEntity = new DrcFolder();
            parentFolderEntity.setName(parentFolder);
            parentFolderEntity.setPath(parentFolderPath.toString());
            parentFolderEntity.setParentFolder(null); // Root folder, no parent
            parentFolderEntity.setCreatedAt(java.time.LocalDateTime.now()); // Manually set created_at
            parentFolderEntity.setUpdatedAt(java.time.LocalDateTime.now()); // Manually set updated_at
            folderRepository.save(parentFolderEntity); // Save the parent folder in DB
        } else {
            parentFolderEntity = existingParentFolder.get();
        }

        // Recursively create subfolders and save them in the database
        if (subfolders != null && !subfolders.isEmpty()) {
            for (SubfolderDTO subfolder : subfolders) {
                // Create the full path for the subfolder
                Path subfolderPath = parentFolderPath.resolve(subfolder.getName());
                createFolderIfNotExists(subfolderPath.toString()); // Create subfolder if it doesn't exist

                // Check if the subfolder already exists in the database under the current parent folder
//                Optional<DrcFolder> existingSubfolder = folderRepository.findByNameAndParentFolderId(subfolder.getName(), parentFolderEntity.getId());
//                if (existingSubfolder.isEmpty()) {
//                    // Create the subfolder entity and set the parent folder
//                    DrcFolder subfolderEntity = new DrcFolder();
//                    subfolderEntity.setName(subfolder.getName());
//                    subfolderEntity.setPath(subfolderPath.toString());
//                    subfolderEntity.setParentFolder(parentFolderEntity); // Set the parent folder
//                    subfolderEntity.setCreatedAt(java.time.LocalDateTime.now()); // Manually set created_at
//                    subfolderEntity.setUpdatedAt(java.time.LocalDateTime.now()); // Manually set updated_at
//
//                    // Save the subfolder in the database
//                    folderRepository.save(subfolderEntity);
//
//                    // Create files in the subfolder (if any)
//                    for (String file : subfolder.getFiles()) {
//                        Path filePath = subfolderPath.resolve(file); // Correctly create file path
//                        createFile(filePath.toString()); // Create file in subfolder (this could be database save too if needed)
//                    }
//
//                    // Recursively create and save nested subfolders
//                    if (subfolder.getSubfolders() != null && !subfolder.getSubfolders().isEmpty()) {
//                        createFolderStructure(subfolderPath.toString(), subfolder.getSubfolders(), "Yes");
//                    }
//                }
             // Check if the subfolder already exists in the database under the current parent folder
                Optional<DrcFolder> existingSubfolder = folderRepository.findByNameAndParentFolderId(subfolder.getName(), parentFolderEntity.getId());
                DrcFolder subfolderEntity;

                if (existingSubfolder.isEmpty()) {
                    // Create the subfolder entity and set the parent folder if it doesn't exist
                    subfolderEntity = new DrcFolder();
                    subfolderEntity.setName(subfolder.getName());
                    subfolderEntity.setPath(subfolderPath.toString());
                    subfolderEntity.setParentFolder(parentFolderEntity); // Set the parent folder
                    subfolderEntity.setCreatedAt(java.time.LocalDateTime.now()); // Manually set created_at
                    subfolderEntity.setUpdatedAt(java.time.LocalDateTime.now()); // Manually set updated_at

                    // Save the subfolder in the database
                    folderRepository.save(subfolderEntity);
                } else {
                    subfolderEntity = existingSubfolder.get();
                }

                // Create files in the subfolder (if any)
                for (String file : subfolder.getFiles()) {
                    Path filePath = subfolderPath.resolve(file); // Correctly create file path
                    createFile(filePath.toString()); // Create file in subfolder (this could be database save too if needed)
                }

                // Recursively create and save nested subfolders if they exist
                if (subfolder.getSubfolders() != null && !subfolder.getSubfolders().isEmpty()) {
                    for (SubfolderDTO nestedSubfolder : subfolder.getSubfolders()) {
                        // Call the createFolderStructure method recursively for nested subfolders
                        createFolderStructure(subfolderPath.toString(), List.of(nestedSubfolder), "Yes");
                    }
                }

            }
        }
    	
    	
        // Creating folders in NFS
//
//        if(subfolders != null && !subfolders.isEmpty()) {
//        	for (SubfolderDTO subfolder : subfolders) {
//                // Create the full path for the subfolder
//                Path subfolderPath = parentFolderPath.resolve(subfolder.getName());
//                createFolderIfNotExists(subfolderPath.toString()); // Create subfolder
//
//                // Create files in the subfolder
//                for (String file : subfolder.getFiles()) {
//                    Path filePath = subfolderPath.resolve(file); // Correctly create file path
//                    createFile(filePath.toString()); // Create file in subfolder
//                }
//
//                // Recursively create nested subfolders
//                if (subfolder.getSubfolders() != null && !subfolder.getSubfolders().isEmpty()) {
//                	createFolderStructure(subfolderPath.toString(), subfolder.getSubfolders(),"Yes");
//                }
//            }
//        }
    }
    
    private void createFolderIfNotExists(String folderPath) throws IOException {
        Path path = Paths.get(folderPath);
        if (Files.notExists(path)) {
            Files.createDirectories(path); // Create the directory if it doesn't exist
        }
    }
    
    private void createFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) {
            Files.createFile(path); // Create the file (you can add file content if needed)
        }
    }

}
