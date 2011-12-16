package com.lulu.batchPDFUpload;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.lulu.publish.client.PublishApiClient;
import com.lulu.publish.client.PublishApiException;
import com.lulu.publish.model.AccessType;
import com.lulu.publish.model.DistributionChannel;
import com.lulu.publish.model.FileDetails;
import com.lulu.publish.model.FileInfo;
import com.lulu.publish.model.Pricing;
import com.lulu.publish.model.ProductType;
import com.lulu.publish.model.Project;
import com.lulu.publish.model.ProjectType;
import com.lulu.publish.response.ErrorResponse;
import com.lulu.publish.response.UploadResponse;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Hello world!
 * 
 */
public class App {
    public static void main(String[] args) throws IOException, PublishApiException, URISyntaxException, InterruptedException {
        App app = new App();

        String filename = args[0];
        System.out.println("Processing " + filename);
        CSVReader reader = new CSVReader(new FileReader(filename), ',', '"', 1);
        String[] nextLine;
        // Skip over the header line.
        while ((nextLine = reader.readNext()) != null) {
            BookMetaData metadata = new BookMetaData();
            int i = 0;
            metadata.setTitle(nextLine[i++].trim());
            metadata.setAuthorFirst(nextLine[i++].trim());
            metadata.setAuthorLast(nextLine[i++].trim());
            metadata.setCategory(new Integer(nextLine[i++].trim()));
            metadata.setDesc(nextLine[i++].trim());
            metadata.setPublisher(nextLine[i++].trim());
            metadata.setLanguage(nextLine[i++].trim());
            metadata.setCountry(nextLine[i++].trim());
            metadata.setEdition(nextLine[i++].trim());
            metadata.setFilePath(nextLine[i++].trim());
            metadata.setCoverPath(nextLine[i++].trim());
            metadata.setLicense(nextLine[i++].trim());
            metadata.setCopyrightYear(new Integer(nextLine[i++].trim()));
            metadata.setKeywords(nextLine[i++].trim());

            System.out.println("Publishing " + metadata.getTitle());
            app.publish(metadata);
        }

    }

    public void publish(BookMetaData metadata) throws IOException, PublishApiException, URISyntaxException, InterruptedException {
        PublishApiClient client = new PublishApiClient("configuration.properties");

        client.login();

        File interiorFile = new File(metadata.getFilePath());
        File coverFile = new File(metadata.getCoverPath());

        UploadResponse uploadInteriorFiles = client.upload(interiorFile);
        UploadResponse uploadCoverResponse = client.upload(coverFile);

        FileInfo fileInfo = new FileInfo();
        List<FileDetails> contents = new ArrayList<FileDetails>();
        FileDetails content = new FileDetails();
        content.setFileId(uploadInteriorFiles.getFileId());
        contents.add(content);
        fileInfo.setContents(contents);
        List<FileDetails> covers = new ArrayList<FileDetails>();
        FileDetails cover = new FileDetails();
        cover.setFileId(uploadCoverResponse.getFileId());
        covers.add(cover);
        fileInfo.setCover(covers);

        Project project = createProjectStructure(metadata);
        project.setFileInfo(fileInfo);

        try {
            project = client.create(project);
            System.out.println("Created project with content id " + project.getContentId());
        } catch (PublishApiException e) {
            ErrorResponse error = client.getError();
            System.out.println(String.format("Got creation error (%s): %s", error.getErrorType(), error.getErrorValue()));
        }
    }

    private Project createProjectStructure(BookMetaData metadata) {
        Project project = new Project();
        project.setProjectType(ProjectType.EBOOK);
        project.setAllowRatings(true);

        project.setBibliography(metadata.getBibliography());
        project.setAccessType(AccessType.PRIVATE);
        List<Pricing> pricingList = new ArrayList<Pricing>();
        Pricing download = new Pricing();
        download.setProductType(ProductType.DOWNLOAD);
        download.setCurrencyCode(Pricing.CURRENCY_CODE_USD);
        download.setTotalPrice(new BigDecimal(0.00));
        pricingList.add(download);
        project.setPricingList(pricingList);

        List<DistributionChannel> distribution = new ArrayList<DistributionChannel>();
        distribution.add(DistributionChannel.LULU_MARKETPLACE);
        project.setDistribution(distribution);
        return project;
    }
}
