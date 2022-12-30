package bookmap.service.impl;

import bookmap.service.FileWriterService;
import bookmap.storage.ReportContainer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TxtWriterServiceImpl implements FileWriterService {

    @Override
    public void write(String filePath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(ReportContainer.get());
        } catch (IOException e) {
            throw new RuntimeException("Can't write info to - " + filePath, e);
        }
    }
}
