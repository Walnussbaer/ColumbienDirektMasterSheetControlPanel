package de.columbien_direkt.mastersheet.control_panel.core.service;

import de.columbien_direkt.mastersheet.control_panel.core.exception.MasterSheetConnectionException;
import jakarta.annotation.Nullable;
import java.io.File;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class MasterSheetConnectionService {

  @Nullable
  private File masterSheetFile;

  @Nullable
  private Workbook masterSheet;

  public MasterSheetConnectionService() {}

  public void setMasterSheetFile(@Nullable File masterSheetFile) {
    this.masterSheetFile = masterSheetFile;
  }

  public Workbook openMasterSheetConnection()
    throws MasterSheetConnectionException {
    if (this.masterSheetFile == null) {
      throw new MasterSheetConnectionException(
        "You must provide a master sheet file for this service to operate."
      );
    }

    try {
      this.masterSheet = new XSSFWorkbook(this.masterSheetFile);
      return this.masterSheet;
    } catch (Exception anyException) {
      throw new MasterSheetConnectionException(
        "Cannot connect to master sheet",
        anyException
      );
    }
  }

  @Nullable
  public Optional<Workbook> getMasterSheet() {
    return Optional.ofNullable(this.masterSheet);
  }

  @Nullable
  public File getMasterSheetFile() {
    return masterSheetFile;
  }

  public boolean isMasterSheetFileExisting() {
    if (this.masterSheetFile == null) {
      return false;
    } else {
      return this.masterSheetFile.isFile();
    }
  }
}
