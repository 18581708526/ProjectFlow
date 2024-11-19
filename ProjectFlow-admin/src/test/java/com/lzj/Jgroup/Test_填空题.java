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
public class Test_填空题 {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\LZJ\\Desktop\\新建文本文档.html"; // 替换为你的文件路径

        try {
            // 读取文件内容
            String html = new String(Files.readAllBytes(Paths.get(filePath)));
            Document document = Jsoup.parse(html);
            List<FillInQuestion> fillInQuestions = parseFillInQuestions(document);

            for (FillInQuestion question : fillInQuestions) {
                System.out.println("题目: " + question.getQuestionText());
                System.out.println("你的答案: " + question.getUserAnswer());
                System.out.println("正确答案: " + question.getCorrectAnswer());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<FillInQuestion> parseFillInQuestions(Document document) {
        List<FillInQuestion> questions = new ArrayList<>();
        Elements questionElements = document.select(".questionLi");

        for (Element questionElement : questionElements) {
            FillInQuestion question = new FillInQuestion();

            // 获取题目文本
            Element questionTextElement = questionElement.selectFirst(".mark_name");
            if (questionTextElement != null) {
                question.setQuestionText(questionTextElement.text());
            }

            // 获取你的答案
            Element yourAnswerElement = questionElement.selectFirst(".mark_fill.colorDeep dd .selectable-text p");
            if (yourAnswerElement != null) {
                question.setUserAnswer(yourAnswerElement.text().trim());
            }

            // 获取正确答案
            Element correctAnswerElement = questionElement.selectFirst(".mark_fill.colorGreen dd");
            if (correctAnswerElement != null) {
                question.setCorrectAnswer(correctAnswerElement.text().trim());
            }

            questions.add(question);
        }

        return questions;
    }

    static class FillInQuestion {
        private String questionText;
        private String userAnswer;
        private String correctAnswer;

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        @Override
        public String toString() {
            return "FillInQuestion{" +
                    "questionText='" + questionText + '\'' +
                    ", userAnswer='" + userAnswer + '\'' +
                    ", correctAnswer='" + correctAnswer + '\'' +
                    '}';
        }
    }
}
