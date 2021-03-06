package com.anthonyG.apps.eventhandlers;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.anthonyG.apps.prueba2_anthony_lopez_guerrero.MainActivityFragment_BALG;
import com.anthonyG.apps.prueba2_anthony_lopez_guerrero.R;
import com.anthonyG.apps.prueba2_anthony_lopez_guerrero.ResultsDialogFragment_BALG;
import com.anthonyG.apps.lifecyclehelpers.QuizViewModel_BALG;

public class GuessButtonListener_BALG implements OnClickListener {
    private MainActivityFragment_BALG mainActivityFragmentBALG;
    private Handler handler;

    public GuessButtonListener_BALG(MainActivityFragment_BALG mainActivityFragmentBALG) {
        this.mainActivityFragmentBALG = mainActivityFragmentBALG;
        this.handler = new Handler();
    }

    @Override
    public void onClick(View v) {
        Button guessButton = ((Button) v);
        String guess = guessButton.getText().toString();
        String answer = this.mainActivityFragmentBALG.getQuizViewModel().getCorrectCountryName();
        this.mainActivityFragmentBALG.getQuizViewModel().setTotalGuesses(1);

        if (guess.equals(answer)) {
            this.mainActivityFragmentBALG.getQuizViewModel().setCorrectAnswers(1);
            this.mainActivityFragmentBALG.getAnswerTextView().setText(answer + "!");
            this.mainActivityFragmentBALG.getAnswerTextView().setTextColor(
                    this.mainActivityFragmentBALG.getResources().getColor(R.color.correct_answer));

            this.mainActivityFragmentBALG.disableButtons();

            if (this.mainActivityFragmentBALG.getQuizViewModel().getCorrectAnswers()
                    == QuizViewModel_BALG.getFlagsInQuiz()) {
                ResultsDialogFragment_BALG quizResults = new ResultsDialogFragment_BALG();
                quizResults.setCancelable(false);
                try {
                    quizResults.show(this.mainActivityFragmentBALG.getChildFragmentManager(), "Quiz Results");
                } catch (NullPointerException e) {
                    Log.e(QuizViewModel_BALG.getTag(),
                            "GuessButtonListener: this.mainActivityFragment.getFragmentManager() " +
                                    "returned null",
                            e);
                }
            } else {
                this.handler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                mainActivityFragmentBALG.animate(true);
                            }
                        }, 2000);
            }
        } else {
            this.mainActivityFragmentBALG.incorrectAnswerAnimation();
            guessButton.setEnabled(false);
        }
    }
}
