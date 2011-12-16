package com.lulu.batchPDFUpload;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lulu.publish.model.Author;
import com.lulu.publish.model.Bibliography;

public class BookMetaData {
    private String filePath;
    private String coverPath;
    private String title;
    private String authorFirst;
    private String authorLast;
    private Integer category;
    private String desc;
    private List<String> keywords;
    private String license;
    private Integer copyrightYear;
    private String publisher;
    private String edition;
    private String language;
    private String country;

    private Author getAuthor() {
        Author author = new Author();
        author.setFirstName(authorFirst);
        author.setLastName(authorLast);
        return author;
    }

    public Bibliography getBibliography() {
        Bibliography bibliography = new Bibliography();
        bibliography.setTitle(title);
        List<Author> authors = new ArrayList<Author>();
        authors.add(getAuthor());
        bibliography.setAuthors(authors);
        bibliography.setCategory(category);
        bibliography.setDescription(desc);
        bibliography.setKeywords(keywords);
        bibliography.setLicense(license);
        bibliography.setCopyrightYear(copyrightYear);
        bibliography.setCopyrightCitation("by " + publisher);
        bibliography.setPublisher(publisher);
        bibliography.setEdition(edition);
        bibliography.setLanguage(language);
        bibliography.setCountryCode(country);
        return bibliography;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorFirst() {
        return authorFirst;
    }

    public void setAuthorFirst(String authorFirst) {
        this.authorFirst = authorFirst;
    }

    public String getAuthorLast() {
        return authorLast;
    }

    public void setAuthorLast(String authorLast) {
        this.authorLast = authorLast;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywordsStr) {
        String[] stringarr = keywordsStr.split(",");
        keywords = Arrays.asList(stringarr);
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(Integer copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
