package de.columbien_direkt.mastersheet.control_panel.core.sample;

import de.columbien_direkt.mastersheet.control_panel.core.exception.MasterSheetOperationException;
import de.columbien_direkt.mastersheet.control_panel.core.service.MasterSheetConnectionService;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

@Service
public class MasterSheetSampleService {

  private final MasterSheetConnectionService masterSheetConnectionService;

  public MasterSheetSampleService(
    MasterSheetConnectionService masterSheetConnectionService
  ) {
    this.masterSheetConnectionService = masterSheetConnectionService;
  }

  public List<String> getSheetNames() throws MasterSheetOperationException {
    try (
      Workbook masterSheet = this.masterSheetConnectionService.openMasterSheetConnection()
    ) {
      var sheetNames = new ArrayList<String>();

      var sheetCount = masterSheet.getNumberOfSheets();

      if (sheetCount == 0) {
        return sheetNames;
      } else {
        for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
          sheetNames.add(masterSheet.getSheetName(sheetIndex));
        }
      }

      return sheetNames;
    } catch (Exception exception) {
      throw new MasterSheetOperationException(
        "Cannot provide sheet names",
        exception
      );
    }
  }
}
