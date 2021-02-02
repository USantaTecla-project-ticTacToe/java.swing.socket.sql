package usantatecla.tictactoe.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import usantatecla.tictactoe.models.Game;
import usantatecla.tictactoe.types.Color;
import usantatecla.tictactoe.views.ViewFactory;
import usantatecla.tictactoe.views.console.BoardView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// TODO Revisar entero con Luis
@ExtendWith(MockitoExtension.class)
public abstract class ControllerTest {

    @Mock
    protected BoardView boardView;

    @Spy
    protected ViewFactory viewFactory;

    @Spy
    protected Game game;

    protected Controller controller;

    @Test
    public void testGivenControllerWhenWriteBoardThenCorrectColorsCaptured() {
        when(this.viewFactory.createBoardView()).thenReturn(this.boardView);
        doReturn(
                Color.X, Color.NULL, Color.NULL,
                Color.NULL, Color.O, Color.NULL,
                Color.O, Color.NULL, Color.X
        ).when(this.game).getColor(any());
        String board =
                "X  " +
                " O " +
                "O X";
        this.controller.writeBoard();
        ArgumentCaptor<Color> argumentCaptor = ArgumentCaptor.forClass(Color.class);
        verify(this.boardView, atLeastOnce()).set(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues(), is(this.stringToColors(board)));
        verify(this.boardView).write();
    }

    protected List<Color> stringToColors(String board) {
        List<Color> colors = new ArrayList<>();
        for (char character : board.toCharArray()) {
            colors.add(this.charToColor(character));
        }
        return colors;
    }

    protected Color charToColor(char character) {
        switch (character) {
            case 'X':
                return Color.X;
            case 'O':
                return Color.O;
            default:
                return Color.NULL;
        }
    }

}
