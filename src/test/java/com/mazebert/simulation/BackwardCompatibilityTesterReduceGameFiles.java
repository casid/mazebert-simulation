package com.mazebert.simulation;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class BackwardCompatibilityTesterReduceGameFiles {

    private static final int version = 21;

    private static final int limit = 5000;
    private static final Path gamesDirectory = Paths.get("games");

    private static List<Path> allFiles;
    private static List<Path> files;

    public static void main(String[] args) throws IOException {
        findFiles();
        printFileSize();

        removeFilesWithSameId();
        printFileSize();

        removeFilesAtRandom();
        printFileSize();

        deleteFiles();
    }

    private static void findFiles() throws IOException {
        System.out.println("Looking for *.mbg files in " + gamesDirectory.toAbsolutePath() + " ...");
        files = Files.walk(gamesDirectory.resolve("" + version), 1).filter(p -> p.toString().endsWith(".mbg")).collect(Collectors.toList());
        if (files.size() <= limit) {
            System.out.println("No more than " + limit + " games found, will exit.");
            System.exit(0);
        }

        allFiles = files;
    }

    private static void printFileSize() {
        System.out.println("Got " + files.size() + " files.");
        System.out.println();
    }

    private static void removeFilesWithSameId() {
        System.out.println("Removing files with duplicate game ids...");

        List<Path> remainingFiles = new ArrayList<>();
        Set<String> ids = new HashSet<>();

        for (Path file : files) {
            String fileName = file.getFileName().toString();
            String id = fileName.substring(0, fileName.lastIndexOf("-"));

            if (ids.add(id)) {
                remainingFiles.add(file);
            }
        }

        files = remainingFiles;
    }

    private static void removeFilesAtRandom() {
        System.out.println("Removing files at random until limit is reached ...");

        Collections.shuffle(files);
        files = files.subList(0, limit);
    }

    private static void deleteFiles() {
        System.out.println("Deleting files...");

        Set<Path> filesToKeep = new HashSet<>(files);

        for (Path file : allFiles) {
            if (!filesToKeep.contains(file)) {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        }

        int deletedFiles = allFiles.size() - files.size();
        System.out.println("Deleted " + deletedFiles + ".");
    }
}
