package com.cuizhanming.template.ai.templatespringai.utils;


import java.io.*;
import java.util.*;

public class ArticleManager {

    private List<String> pages;
    private List<String> pageLines;
    private List<String> words;
    private int paidPages;
    private Map<String, Integer> paymentStructure;
    private String articleText;
    private Integer wordsPerLine;
    private Integer linesPerPage;

    private ArticleManager() {
        this.pages = new ArrayList<>();
        this.pageLines = new ArrayList<>();
        this.paidPages = 0;
        this.words = new ArrayList<>();
    }

    public ArticleManager(String articleText, Map<String, Object> articleMap) {
        this();
        this.articleText = articleText;
        this.wordsPerLine = (Integer) articleMap.get("wordsPerLine");
        this.linesPerPage = (Integer) articleMap.get("linesPerPage");
        this.paymentStructure = (Map<String, Integer>) articleMap.get("paymentStructure");
    }

    public void splitIntoPages() {
        System.out.println("Splitting article into pages...");

        if (!isValidString(articleText)) {
            System.out.println("Invalid line input: contains non-string characters");
            throw new IllegalArgumentException("Invalid line input: contains non-string characters");
        }

        try (BufferedReader reader = new BufferedReader(new StringReader(articleText))) {
            String line;
            while ((line = reader.readLine()) != null) {


                var lineWords = Arrays.asList(line.trim().split("\\s+"));
                words.addAll(lineWords);
                System.out.println("Split line:" + line);

                for (int i = 0; i < lineWords.size(); i += wordsPerLine) {
                    int end = Math.min(i + wordsPerLine, lineWords.size());
                    pageLines.add(String.join(" ", lineWords.subList(i, end)));
                    System.out.println("Add another line to current page: " + pageLines.size());
                }
            }

            System.out.println("Calculating pages...");
            for (int i = 0; i < pageLines.size(); i += linesPerPage) {
                int end = Math.min(i + linesPerPage, pageLines.size());
                pages.add(String.join("\n", pageLines.subList(i, end)));
                System.out.println("Add another page: " + pages.size());
            }

        } catch (IOException e) {
            System.out.println("Error occurred while splitting article into pages: " + e.getMessage());
        }
    }

    private boolean isValidString(String line) {
        return line.matches("^[a-zA-Z0-9\\s]*$");
    }

    public int calculatePayment() {
        paidPages = words.size() / (wordsPerLine * linesPerPage);
        if (paidPages < 1) {
            return 0;
        } else if (paymentStructure.containsKey(String.valueOf(paidPages))) {
            return paymentStructure.get(String.valueOf(paidPages));
        } else {
            return paymentStructure.get("default");
        }
    }

    public void displayPages() {
        int payment = calculatePayment();
        System.out.println("Total Pages: " + pages.size());
        System.out.println("Paid Pages: " + (paidPages > 0 ? paidPages : 0));
        System.out.println("Payment Due: $" + payment);

        for (int i = 0; i < pages.size(); i++) {
            System.out.println("\nPage " + (i + 1) + ":\n" + pages.get(i) + "\n");
        }
    }

    public void processArticle() throws IOException {
        splitIntoPages();
        displayPages();
    }

    private static Map<String, Object> createArticleMap(int wordsPerLine, int linesPerPage, Map<String, Integer> paymentStructure) {
        Map<String, Object> articleOptions = new HashMap<>();
        articleOptions.put("wordsPerLine", wordsPerLine);
        articleOptions.put("linesPerPage", linesPerPage);
        articleOptions.put("paymentStructure", paymentStructure);

        return articleOptions;
    }
    public static Map<String, Integer> addPaymentStructure(Object... entries) {
        Map<String, Integer> paymentStructure = new HashMap<>();

        if (entries.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of arguments; expected pairs of key and value.");
        }

        for (int i = 0; i < entries.length; i += 2) {
            String key = (String) entries[i];
            Integer value = (Integer) entries[i + 1];
            paymentStructure.put(key, value);
        }

        return paymentStructure;
    }

    // Example usage
    public static void main(String[] args) {
        String articleText = "Replace with the actual file content";

        ArticleManager articleManager = new ArticleManager(articleText, createArticleMap(2, 2,
                addPaymentStructure("1", 1, "2", 1, "3", 2, "4", 2, "default", 3)));
        try {
            articleManager.processArticle();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}