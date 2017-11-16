package com.rpl.spring.learn.service;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;


public class FileSystemStorageServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private FileSystemStorageService service;
    private MultipartFile validFile;

    @Before
    public void init() throws IOException {
        service = new FileSystemStorageService(testFolder.getRoot().toPath());
        validFile = new MockMultipartFile("fantasyName", "success", "application/json", new byte[]{0, 0, 0});
    }

    @After
    public void end() {
        testFolder.delete();
    }


    @Test(expected = StorageException.class)
    public void store_emptyFile_throwStorageException() {
        MockMultipartFile file = new MockMultipartFile("emptyFile", new byte[]{});
        service.store(file);
    }

    @Test
    public void store_invalidNameFile_throwStorageException() {
        thrown.expect(StorageException.class);
        thrown.expectMessage(CoreMatchers.containsString("Cannot store file with relative path"));
        MockMultipartFile file = new MockMultipartFile("fantasyName", "../error", "application/json", new byte[]{0, 0, 0});
        service.store(file);
    }

    @Test
    public void store_validFile_store() throws IOException {
        service.store(validFile);
        File[] filesInFolder = testFolder.getRoot().listFiles();
        assertThat(filesInFolder, notNullValue());
        Optional<File> fileOptional = Stream.of(filesInFolder).filter(file -> file.getName().contains("success")).findAny();
        assertThat(true, is(fileOptional.isPresent()));
    }

    @Test
    public void loadAll_emptyFolder_returnEmptyStream() {
        Stream<Path> pathStream = service.loadAll();
        assertEquals("not empty folder", 0L, pathStream.count());
    }

    @Test
    public void loadAll_multiFiles_returnPopulateStream() throws IOException {
        for (int i = 0; i < 10; i++) {
            Files.createTempFile(testFolder.getRoot().toPath(), "teste" + i, "jnuit");
        }
        Stream<Path> pathStream = service.loadAll();
        assertNotEquals("empty folder", 0L, pathStream.count());
    }

    @Test
    public void loadAsResource_notExistFile_throwStorageException() {
        thrown.expect(StorageException.class);
        thrown.expectMessage("File not exists or don't have permissions");
        service.loadAsResource("fail");
    }

    @Test
    public void loadAsResource_validFile_resource() throws IOException {
        Path tempFile = Files.createTempFile(testFolder.getRoot().toPath(), "teste", "jnuit");
        Resource resource = service.loadAsResource(tempFile.toString());
        assertThat(resource, notNullValue());
    }
}