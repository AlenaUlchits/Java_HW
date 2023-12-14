package ru.geekbrains.lesson5;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupUtility {

    public static void backupDirectory(String sourceDir) {
        String backupDirPath = "./backup";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        String backupSubdir = backupDirPath + File.separator + "backup_" + timestamp;
        new File(backupSubdir).mkdirs();

        FileVisitor<Path> fileVisitor = new FileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path sourcePath = Paths.get(sourceDir);
                Path targetDir = Paths.get(backupSubdir, sourceDir.equals(dir.toString()) ? "" : sourcePath.relativize(dir).toString());
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // Копируем файл в резервную копию
                Path sourcePath = Paths.get(sourceDir);
                Path targetFile = Paths.get(backupSubdir, sourcePath.relativize(file).toString());
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        };

        try {
            Files.walkFileTree(Paths.get(sourceDir), fileVisitor);
            System.out.println("Резервная копия создана в " + backupSubdir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String sourceDirectory = ".";
        backupDirectory(sourceDirectory);
    }
}