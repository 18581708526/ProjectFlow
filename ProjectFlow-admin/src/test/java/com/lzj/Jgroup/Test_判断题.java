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
public class Test_判断题 {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\LZJ\\Desktop\\新建文本文档.html"; // 替换为你的文件路径

        try {
            // 读取文件内容
            String html = new String(Files.readAllBytes(Paths.get(filePath)));
            Document document = Jsoup.parse(html);

            List<TrueFalseQuestion> trueFalseQuestions = parseTrueFalseQuestions(document);

            // 输出判断题
            for (TrueFalseQuestion question : trueFalseQuestions) {
                System.out.println("题目: " + question.getQuestionText());
                System.out.println("你的答案: " + question.getUserAnswer());
                System.out.println("正确答案: " + question.getCorrectAnswer());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<TrueFalseQuestion> parseTrueFalseQuestions(Document document) {
        List<TrueFalseQuestion> questions = new ArrayList<>();
        Elements questionElements = document.select(".questionLi");

        for (Element questionElement : questionElements) {
            // 检查是否为判断题
            Element questionTextElement = questionElement.selectFirst(".mark_name");
            if (questionTextElement != null && questionTextElement.text().contains("(判断题")) {
                TrueFalseQuestion question = new TrueFalseQuestion();

                // 获取题目文本
                question.setQuestionText(questionTextElement.text());

                // 获取你的答案
                Element yourAnswerElement = questionElement.selectFirst(".mark_key .colorDeep");
                if (yourAnswerElement != null) {
                    question.setUserAnswer(yourAnswerElement.text().split(":")[1].trim());
                }

                // 获取正确答案
                Element correctAnswerElement = questionElement.selectFirst(".mark_key .colorGreen");
                if (correctAnswerElement != null) {
                    question.setCorrectAnswer(correctAnswerElement.text().split(":")[1].trim());
                }

                questions.add(question);
            }
        }

        return questions;
    }

    static class TrueFalseQuestion {
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
            return "TrueFalseQuestion{" +
                    "questionText='" + questionText + '\'' +
                    ", userAnswer='" + userAnswer + '\'' +
                    ", correctAnswer='" + correctAnswer + '\'' +
                    '}';
        }
    }
}
