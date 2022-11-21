package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.ImportReceipt;
import s1mple.dlowji.ffms_refactor.repositories.ImportRepository;
import s1mple.dlowji.ffms_refactor.services.IImportReceiptService;

import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

@Service
public class IImportReceiptServiceImpl implements IImportReceiptService {

    @Autowired
    private ImportRepository importRepository;

    @Override
    public List<ImportReceipt> getImportReceiptsByQuarter(int quarter, int year) {
        List<ImportReceipt> importReceiptList = importRepository.findAll();

        for (ImportReceipt receipt: importReceiptList) {
            if (receipt.getCreatedAt().getYear() != year || receipt.getCreatedAt().get(IsoFields.QUARTER_OF_YEAR) != quarter) {
                importReceiptList.remove(receipt);
            }
        }

        return importReceiptList;
    }

    @Override
    public int getImportReceiptsByMonth(int month, int year) {
        List<ImportReceipt> importReceiptList = new ArrayList<>();

        for (ImportReceipt receipt: importRepository.findAll()) {
            if (receipt.getCreatedAt().getYear() == year && receipt.getCreatedAt().getMonthValue() == month) {
                importReceiptList.add(receipt);
            }
        }

        int totalPrice = 0;

        for (ImportReceipt receipt:importReceiptList) {
            totalPrice += receipt.getTotalPrice();
        }

        return totalPrice;
    }

    @Override
    public int getImportReceiptsByDay(int day, int month, int year) {
        List<ImportReceipt> importReceiptList = new ArrayList<>();

        for (ImportReceipt receipt: importRepository.findAll()) {
            if (receipt.getCreatedAt().getYear() == year && receipt.getCreatedAt().getMonthValue() == month && receipt.getCreatedAt().getDayOfMonth() == day) {
                importReceiptList.add(receipt);
            }
        }

        int totalPrice = 0;

        for (ImportReceipt receipt:importReceiptList) {
            totalPrice += receipt.getTotalPrice();
        }

        return totalPrice;
    }
}
