package com.traulka.app.service.impl;

import com.traulka.app.dto.PersonDto;
import com.traulka.app.service.FileService;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.traulka.app.util.NormalizationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
public class PersonExcelFileService implements FileService<PersonDto> {

    private static final int FIRST_PAGE_NUMBER = 0;
    private static final int FIRST_NAME_COLUMN_NUMBER = 0;
    private static final int SURNAME_COLUMN_NUMBER = 1;
    private static final int ADDRESS1_COLUMN_NUMBER = 2;
    private static final int ADDRESS2_COLUMN_NUMBER = 3;
    private static final int CITY_COLUMN_NUMBER = 4;
    private static final int STATE_COLUMN_NUMBER = 5;
    private static final int POST_CODE_COLUMN_NUMBER = 6;
    private static final int COUNTRY_CODE_COLUMN_NUMBER = 7;
    private static final int GENDER_COLUMN_NUMBER = 8;
    private static final int DATE_OF_BIRTH_COLUMN_NUMBER = 9;
    private static final String DATE_PATTERN = "d/M/yyyy";

    @Override
    public List<PersonDto> getData(MultipartFile file) {
        List<PersonDto> personList = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(FIRST_PAGE_NUMBER);
            Iterator<Row> iterator = sheet.iterator();

            skipHeader(iterator);
            personList = collectPersons(iterator);

        } catch (IOException e) {
            log.error(String.format("File with name %s cannot be parsed", file.getName()));
        }
        return personList;
    }

    private void skipHeader(Iterator<Row> iterator) {
        iterator.next();
    }

    private List<PersonDto> collectPersons(Iterator<Row> iterator) {
        return Stream.iterate(iterator, Iterator::hasNext, UnaryOperator.identity())
            .map(Iterator::next)
            .map(this::toPersonDto)
            .collect(Collectors.toList());
    }

    private PersonDto toPersonDto(Row row){
        String normalizedPostCode = NormalizationUtil.normalizePostCode(getValue(row.getCell(POST_CODE_COLUMN_NUMBER)));
        String normalizeAddress = NormalizationUtil.normalizeAddress(getValue(row.getCell(ADDRESS1_COLUMN_NUMBER)));
        String normalizedState = NormalizationUtil.normalizeState(getValue(row.getCell(STATE_COLUMN_NUMBER)));
        return PersonDto.builder()
            .setFirstName(getValue(row.getCell(FIRST_NAME_COLUMN_NUMBER)))
            .setSurname(getValue(row.getCell(SURNAME_COLUMN_NUMBER)))
            .setAddress1(normalizeAddress)
            .setAddress2(getValue(row.getCell(ADDRESS2_COLUMN_NUMBER)))
            .setCity(getValue(row.getCell(CITY_COLUMN_NUMBER)))
            .setState(normalizedState)
            .setPostCode(normalizedPostCode)
            .setCountryCode(getValue(row.getCell(COUNTRY_CODE_COLUMN_NUMBER)))
            .setGender(getValue(row.getCell(GENDER_COLUMN_NUMBER)))
            .setDateOfBirth(getValue(row.getCell(DATE_OF_BIRTH_COLUMN_NUMBER)))
            .build();
    }

    private String getValue(Cell cell) {
        return ofNullable(cell)
                .map(this::parseCell)
                .orElse(null);
    }

    private String parseCell(Cell cell) {

        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC: {
                return parseNumeric(cell);
            }
            case STRING: {
                return parseString(cell);
            }
            default:
               log.error("Cannot be parsed");
        }
        return null;
    }

    private String parseString(Cell cell) {
        return cell.getStringCellValue();
    }

    private String parseNumeric(Cell cell) {
        DataFormatter dataFormatter = new DataFormatter();
        String formattedCellStr = dataFormatter.formatCellValue(cell);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate localDate = LocalDate.parse(formattedCellStr, formatter);
        return String.valueOf(localDate);
    }
}
