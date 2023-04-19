package my.edu.utar.periodtrackertowl;

public class QuestionAnswer {
    private String question;
    private String answer;

    public QuestionAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
