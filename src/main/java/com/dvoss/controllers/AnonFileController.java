package com.dvoss.controllers;

import com.dvoss.entities.AnonFile;
import com.dvoss.services.AnonFileRepository;
import com.dvoss.utilities.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Dan on 6/27/16.
 */
@Controller
public class AnonFileController {
    @Autowired
    AnonFileRepository files;

    @PostConstruct
    public void init() throws SQLException {
        Server.createWebServer().start();
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile file, String comments, boolean makePermanent, String password) throws IOException, PasswordStorage.CannotPerformOperationException {
        File dir = new File("public/files");
        dir.mkdirs();

        File uploadedFile = File.createTempFile("file", file.getOriginalFilename(), dir);
        FileOutputStream fos = new FileOutputStream(uploadedFile);
        fos.write(file.getBytes());

        AnonFile anonFile = new AnonFile(file.getOriginalFilename(), uploadedFile.getName(), comments, makePermanent, PasswordStorage.createHash(password));

        if (files.count() <= 9) {
            files.save(anonFile);
        }
        else if (anonFile.isPermanent()) {
            files.save(anonFile);
        }
        else {
            AnonFile fileInDb = files.findFirstByIsPermanentFalseOrderByIdAsc();
            File fileOnDisk = new File(dir, fileInDb.getRealFilename());
            fileOnDisk.delete();
            files.delete(files.findFirstByIsPermanentFalseOrderByIdAsc());
            files.save(anonFile);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String delete(String fileName, String password) throws Exception {
        AnonFile af = files.findByComments(fileName);
        if (!PasswordStorage.verifyPassword(password, af.getPassword())) {
            throw new Exception("Wrong password");
        }
        else if (password == null) {
            throw new Exception("Password not sent.");
        }
        else {
            files.delete(af);
            File fileOnDisk = new File("public/files/" + af.getRealFilename());
            fileOnDisk.delete();
        }
        return "redirect:/";
    }
}
