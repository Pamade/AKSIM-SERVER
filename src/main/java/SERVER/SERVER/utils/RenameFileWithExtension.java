package SERVER.SERVER.utils;

public class RenameFileWithExtension {
    public static String rename(String originalFilename) {
        String id = String.valueOf(System.currentTimeMillis());
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("Original filename cannot be null or empty");
        }

        int lastDotIndex = originalFilename.lastIndexOf('.');

        if (lastDotIndex == -1) {
            // If there's no extension, append the ID to the file name
            return originalFilename + id;
        }

        // Split the file name into the name without extension and the extension
        String fileNameWithoutExtension = originalFilename.substring(0, lastDotIndex);
        String fileExtension = originalFilename.substring(lastDotIndex);

        // Combine the file name without extension, the ID, and the extension
        return fileNameWithoutExtension + id + fileExtension;
    }
}
