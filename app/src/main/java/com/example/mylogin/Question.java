package com.example.mylogin;

public class Question {
    private String A;
    private String B;
    private String C;
    private String Correct;
    private String D;
    private int Level;
    private String Question;


    private String QuestionTypeConventional3PhaseG9;
    private String QuestionEnglish;
    private String Section;
    private int SrNo;
    private String Topic;

    // Default constructor required for Firebase deserialization
    public Question() {
    }

    // Getters and setters
    public String getA() {
        return A;
    }

    public void setA(String A) {
        this.A = A;
    }

    public String getB() {
        return B;
    }

    public void setB(String B) {
        this.B = B;
    }

    public String getC() {
        return C;
    }

    public void setC(String C) {
        this.C = C;
    }

    public String getCorrect() {
        return Correct;
    }

    public void setCorrect(String Correct) {
        this.Correct = Correct;
    }

    public String getD() {
        return D;
    }

    public void setD(String D) {
        this.D = D;
    }

    public int getLevel() {
        return Level;
    }
    public String getQuestionEnglish() {
        return QuestionEnglish;
    }

    public void setQuestionEnglish(String questionEnglish) {
        QuestionEnglish = questionEnglish;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getQuestionTypeConventional3PhaseG9() {
        return QuestionTypeConventional3PhaseG9;
    }

    public void setQuestionTypeConventional3PhaseG9(String QuestionTypeConventional3PhaseG9) {
        this.QuestionTypeConventional3PhaseG9 = QuestionTypeConventional3PhaseG9;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String Section) {
        this.Section = Section;
    }

    public int getSrNo() {
        return SrNo;
    }

    public void setSrNo(int SrNo) {
        this.SrNo = SrNo;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String Topic) {
        this.Topic = Topic;
    }
}
