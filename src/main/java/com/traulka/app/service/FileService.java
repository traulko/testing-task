package com.traulka.app.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService<T> {

    List<T> getData(MultipartFile file);

}
