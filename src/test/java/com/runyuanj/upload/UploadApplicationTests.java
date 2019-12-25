package com.runyuanj.upload;

import com.runyuanj.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@SpringBootTest(classes = {UploadApplication.class})
class UploadApplicationTests {

    @Autowired
    private UploadService uploadService;

    @Test
    void testUploadFile() throws Exception {
        MultipartFile[] files = new MultipartFile[1];

        File file = new File("D:\\usr\\share\\nginx\\html\\runyu\\video\\2019\\12\\25\\2d61adf700b241c5a7cc95c11ab4201c.mp4");

        MockMultipartFile mockMultipartFile = new MockMultipartFile(file.getName(), file.getName(),
                "video", new FileInputStream(file));

        files[0] = mockMultipartFile;
        List<String> strings = uploadService.uploadFile(files);
        Assert.state(strings != null && !strings.isEmpty()
                && StringUtils.isNotBlank(strings.get(0)), "upload failed");
    }

}
