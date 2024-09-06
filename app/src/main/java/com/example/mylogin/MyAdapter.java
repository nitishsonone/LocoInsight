package com.example.mylogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Question> list;
    boolean showAnswer = false;  // To track whether to show the answer
    Map<Integer, Integer> userSelections = new HashMap<>();  // To track user selections
    private ArrayList<Question> filteredQuestions;


    public MyAdapter(Context context, ArrayList<Question> list) {
        this.context = context;
        this.list = list;
        this.filteredQuestions = new ArrayList<>(list); // Initialize filteredQuestions with all questions

    }



    public void setShowAnswer(boolean showAnswer) {
        this.showAnswer = showAnswer;
    }

    public void clearSelections() {
        userSelections.clear();
    }

    public void filterBySection(String section, String type) {
        filteredQuestions.clear();
        for (Question question : list) {
            if (question.getSection().equals(section) && question.getQuestionTypeConventional3PhaseG9().equals(type)) {
                filteredQuestions.add(question);
            }
        }
        notifyDataSetChanged();
    }

    public void filterByLevelAndQuestionType(int level, String questionType) {
        ArrayList<Question> filteredList = new ArrayList<>();
        System.out.println(level);
        System.out.println(questionType);
        for (Question question : list) {
            if (question.getLevel() == level && questionType.equals(question.getQuestionTypeConventional3PhaseG9())) {
                filteredQuestions.add(question);
                System.out.println(question.getLevel());
                System.out.println(question.getQuestionTypeConventional3PhaseG9());
            }
        }
        list.clear();
        list.addAll(filteredList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_qb, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            Question q = filteredQuestions.get(position);
            holder.question.setText((position+1) + ". " + q.getQuestion());
            if(q.getSection().equals("hindi")){
                holder.questionEnglish.setText(q.getQuestionEnglish());
            }else{
                holder.questionEnglish.setText((position+1) + ". " + q.getQuestionEnglish());
            }
            holder.o1.setText(q.getA());
            holder.o2.setText(q.getB());
            holder.o3.setText(q.getC());
            holder.o4.setText(q.getD());

            holder.radioGroup.setOnCheckedChangeListener(null);  // Prevents triggering the listener during setup
            holder.radioGroup.clearCheck();  // Clear previous selection
            holder.o1.setBackgroundColor(Color.TRANSPARENT);
            holder.o2.setBackgroundColor(Color.TRANSPARENT);
            holder.o3.setBackgroundColor(Color.TRANSPARENT);
            holder.o4.setBackgroundColor(Color.TRANSPARENT);

            // Restore user's previous selection
            if (userSelections.containsKey(position)) {
                holder.radioGroup.check(userSelections.get(position));
            }

            // Show correct/incorrect colors if showAnswer is true
            if (showAnswer && userSelections.containsKey(position)) {
                int checkedId = userSelections.get(position);
                RadioButton selectedButton = holder.radioGroup.findViewById(checkedId);
                if (selectedButton != null) {
                    String selectedAnswer = "";
                    switch (checkedId) {
                        case R.id.op1:
                            selectedAnswer = "A";
                            break;
                        case R.id.op2:
                            selectedAnswer = "B";
                            break;
                        case R.id.op3:
                            selectedAnswer = "C";
                            break;
                        case R.id.op4:
                            selectedAnswer = "D";
                            break;
                    }

                    if (selectedAnswer.equals(q.getCorrect())) {
                        selectedButton.setBackgroundResource(R.drawable.option_bg1);
                    } else {
                        selectedButton.setBackgroundResource(R.drawable.option_bg);
                        // Highlight the correct answer
                        switch (q.getCorrect()) {
                            case "A":
                                holder.o1.setBackgroundResource(R.drawable.option_bg1);
                                break;
                            case "B":
                                holder.o2.setBackgroundResource(R.drawable.option_bg1);
                                break;
                            case "C":
                                holder.o3.setBackgroundResource(R.drawable.option_bg1);
                                break;
                            case "D":
                                holder.o4.setBackgroundResource(R.drawable.option_bg1);
                                break;
                        }
                    }
                }
            }

            holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // Store user's selection
                    userSelections.put(position, checkedId);
                    // Reset all colors to transparent
                    holder.o1.setBackgroundColor(Color.TRANSPARENT);
                    holder.o2.setBackgroundColor(Color.TRANSPARENT);
                    holder.o3.setBackgroundColor(Color.TRANSPARENT);
                    holder.o4.setBackgroundColor(Color.TRANSPARENT);

                    // If showAnswer is true, show the result immediately
                    if (showAnswer) {
                        RadioButton selectedButton = group.findViewById(checkedId);
                        if (selectedButton == null) {
                            Log.e("MyAdapter", "selectedButton is null. checkedId: " + checkedId);
                            return;
                        }

                        String selectedAnswer = "";

                        switch (checkedId) {
                            case R.id.op1:
                                selectedAnswer = "A";
                                break;
                            case R.id.op2:
                                selectedAnswer = "B";
                                break;
                            case R.id.op3:
                                selectedAnswer = "C";
                                break;
                            case R.id.op4:
                                selectedAnswer = "D";
                                break;
                            default:
                                Log.e("MyAdapter", "Unknown checkedId: " + checkedId);
                                return;
                        }

                        // Validate the selected answer
                        if (selectedAnswer.equals(q.getCorrect())) {
                            selectedButton.setBackgroundResource(R.drawable.option_bg1);
                        } else {
                            selectedButton.setBackgroundResource(R.drawable.option_bg);

                            // Highlight the correct answer
                            switch (q.getCorrect()) {
                                case "A":
                                    holder.o1.setBackgroundResource(R.drawable.option_bg1);
                                    break;
                                case "B":
                                    holder.o2.setBackgroundResource(R.drawable.option_bg1);
                                    break;
                                case "C":
                                    holder.o3.setBackgroundResource(R.drawable.option_bg1);
                                    break;
                                case "D":
                                    holder.o4.setBackgroundResource(R.drawable.option_bg1);
                                    break;
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            Log.e("MyAdapter", "Error in onBindViewHolder", e);
        }
    }

    @Override
    public int getItemCount() {
        return filteredQuestions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, questionEnglish;
        RadioButton o1, o2, o3, o4;
        RadioGroup radioGroup;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questionEnglish = itemView.findViewById(R.id.eng_ques);
            question = itemView.findViewById(R.id.hindi_ques);
            o1 = itemView.findViewById(R.id.op1);
            o2 = itemView.findViewById(R.id.op2);
            o3 = itemView.findViewById(R.id.op3);
            o4 = itemView.findViewById(R.id.op4);
            radioGroup = itemView.findViewById(R.id.rg);
        }
    }
}
