package com.lzj.Jgroup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//C:\Users\LZJ\Desktop\新建文本文档.html
public class Test_填空题_多个答案 {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\LZJ\\Desktop\\新建文本文档.html"; // 替换为你的文件路径

        try {
            // 读取文件内容
            String html = new String(Files.readAllBytes(Paths.get(filePath)));
            Document document = Jsoup.parse(html);

            List<FillingQuestion> fillingQuestions = parseFillingQuestions(document);

            // 输出填空题
            for (FillingQuestion question : fillingQuestions) {
                System.out.println("题目: " + question.getQuestionText());
                System.out.println("你的答案: " + question.getUserAnswers());
                System.out.println("正确答案: " + question.getCorrectAnswers());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<FillingQuestion> parseFillingQuestions(Document document) {
        List<FillingQuestion> questions = new ArrayList<>();
        Elements questionElements = document.select(".questionLi");

        for (Element questionElement : questionElements) {
            // 检查是否为填空题
            Element questionTextElement = questionElement.selectFirst(".mark_name");
            if (questionTextElement != null && questionTextElement.text().contains("(填空题")) {
                FillingQuestion question = new FillingQuestion();

                // 获取题目文本
                question.setQuestionText(questionTextElement.text());

                // 获取你的答案
                Elements yourAnswerElements = questionElement.select(".mark_fill.colorDeep dd div.selectable-text p");
                List<String> userAnswers = new ArrayList<>();
                for (Element answerElement : yourAnswerElements) {
                    userAnswers.add(answerElement.text());
                }
                question.setUserAnswers(userAnswers);

                // 获取正确答案
                Elements correctAnswerElements = questionElement.select(".mark_fill.colorGreen dd");
                List<String> correctAnswers = new ArrayList<>();
                for (Element answerElement : correctAnswerElements) {
                    correctAnswers.add(answerElement.text().split("\\) ")[1]);
                }
                question.setCorrectAnswers(correctAnswers);

                questions.add(question);
            }
        }

        return questions;
    }

    static class FillingQuestion {
        private String questionText;
        private List<String> userAnswers;
        private List<String> correctAnswers;

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public List<String> getUserAnswers() {
            return userAnswers;
        }

        public void setUserAnswers(List<String> userAnswers) {
            this.userAnswers = userAnswers;
        }

        public List<String> getCorrectAnswers() {
            return correctAnswers;
        }

        public void setCorrectAnswers(List<String> correctAnswers) {
            this.correctAnswers = correctAnswers;
        }

        @Override
        public String toString() {
            return "FillingQuestion{" +
                    "questionText='" + questionText + '\'' +
                    ", userAnswers=" + userAnswers +
                    ", correctAnswers=" + correctAnswers +
                    '}';
        }
    }
}
