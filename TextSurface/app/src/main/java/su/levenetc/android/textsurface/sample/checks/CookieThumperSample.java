package su.levenetc.android.textsurface.sample.checks;

import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Just;
import su.levenetc.android.textsurface.animations.Scale;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;

/**
 * Created by Eugene Levenetc.
 */
public class CookieThumperSample {

    private static int history_num = 12;
    private Text[] m_history2 = new Text[history_num];
    private Paint m_paint;
    private int m_to_disappear = 0;
    private TextSurface textSurface;

    private Text createTextNode(String content, int size, int color, int align, Text alignText) {
        Text node;
        if (align == Align.SURFACE_CENTER || alignText == null) {
            node = TextBuilder
                    .create(content)
                    .setPaint(m_paint)
                    .setSize(size)
                    .setAlpha(0)
                    .setColor(color)
                    .setPosition(align).build();
        }
        else {
            node = TextBuilder
                    .create(content)
                    .setPaint(m_paint)
                    .setSize(size)
                    .setAlpha(0)
                    .setColor(color)
                    .setPosition(align, alignText).build();
        }
        return node;
    }

    private void setArray(String[] content_string, Text[] history) {
        int i;
        for (i = 0; i < history_num; i++)
            history[i] = null;
        history[0] = createTextNode(content_string[0], 62, 0x444444, Align.SURFACE_CENTER, null);
        for (i = 1; i < history_num; i++) {
            if (i % 2 == 0) {
                history[i] = createTextNode(content_string[i / 2], 62, 0x999999 - 0x111111 * i,
                        Align.BOTTOM_OF | Align.CENTER_OF, history[i - 1]);
            }
            else {
                history[i] = createTextNode(" ", 20, 0x999999 - 0x111111 * i,
                        Align.BOTTOM_OF | Align.CENTER_OF, history[i - 1]);
            }
        }
    }

    private void hideLastTime() {
        textSurface.play(
                new Parallel(
                        Alpha.hide(m_history2[2], 20),
                        Alpha.hide(m_history2[4], 20),
                        Alpha.hide(m_history2[6], 20),
                        Alpha.hide(m_history2[8], 20),
                        Alpha.hide(m_history2[10], 20)
                )
        );
    }

    private void setContent(String[] content_string) {
        setArray(content_string, m_history2);
        for (int i = 0; i < history_num; i++) {
            if (i % 2 == 0)
                Log.e("fuck", "$" + m_history2[i].getValue());
        }
    }

    private void refreshUI() {
        textSurface.reset();
        textSurface.play(
                new Sequential(
                        new Parallel(
                                ShapeReveal.create(m_history2[8], 10, SideCut.show(Side.TOP), false),
                                ShapeReveal.create(m_history2[6], 10, SideCut.show(Side.TOP), false),
                                ShapeReveal.create(m_history2[4], 10, SideCut.show(Side.TOP), false),
                                ShapeReveal.create(m_history2[2], 10, SideCut.show(Side.TOP), false),
                                ShapeReveal.create(m_history2[0], 10, SideCut.show(Side.TOP), false),
                                ChangeColor.to(m_history2[8], 10, 0xffffff),
                                ChangeColor.to(m_history2[6], 10, 0xcccccc),
                                new Scale(m_history2[6], 10, 1f, (float) (58.0 / 62.0), Pivot.LEFT),
                                ChangeColor.to(m_history2[4], 10, 0x999999),
                                new Scale(m_history2[4], 10, (float) (58.0 / 62.0), (float) (54.0 / 62.0), Pivot.LEFT),
                                ChangeColor.to(m_history2[2], 10, 0x666666),
                                new Scale(m_history2[2], 10, (float) (54.0 / 62.0), (float) (50.0 / 62.0), Pivot.LEFT),
                                ChangeColor.to(m_history2[0], 10, 0x333333),
                                new Scale(m_history2[0], 10, (float) (50.0 / 62.0), (float) (46.0 / 62.0), Pivot.LEFT),
                                TransSurface.toCenter(m_history2[4], 10)
                        ),
                        Delay.duration(10),
                        new Parallel(
                                Alpha.hide(m_history2[0], 100),
                                Alpha.hide(m_history2[1], 100),
                                ShapeReveal.create(m_history2[10], 500, SideCut.show(Side.LEFT), false),
                                ChangeColor.to(m_history2[8], 500, 0xcccccc),
                                new Scale(m_history2[8], 500, 1f, (float) (58.0 / 62.0), Pivot.LEFT),
                                ChangeColor.to(m_history2[6], 500, 0x999999),
                                new Scale(m_history2[6], 500, (float) (58.0 / 62.0), (float) (54.0 / 62.0), Pivot.LEFT),
                                ChangeColor.to(m_history2[4], 500, 0x666666),
                                new Scale(m_history2[4], 500, (float) (54.0 / 62.0), (float) (50.0 / 62.0), Pivot.LEFT),
                                ChangeColor.to(m_history2[2], 500, 0x333333),
                                new Scale(m_history2[2], 500, (float) (50.0 / 62.0), (float) (46.0 / 62.0), Pivot.LEFT),
                                TransSurface.toCenter(m_history2[6], 500)
                        )
                )
        );
    }

    public void refresh(String[] content_string) {
        hideLastTime();
        setContent(content_string);
        refreshUI();
    }

    public void test() {
        textSurface.play(
                new Parallel(
                        ShapeReveal.create(m_history2[8], 500, SideCut.show(Side.LEFT), false),
                        ShapeReveal.create(m_history2[6], 500, SideCut.show(Side.LEFT), false),
                        ShapeReveal.create(m_history2[4], 500, SideCut.show(Side.LEFT), false),
                        ShapeReveal.create(m_history2[2], 500, SideCut.show(Side.LEFT), false),
                        ShapeReveal.create(m_history2[0], 500, SideCut.show(Side.LEFT), false)
                )
        );
    }

    public CookieThumperSample(AssetManager assetManager, TextSurface textSurface) {
        final Typeface robotoBlack = Typeface.createFromAsset(assetManager, "fonts/Roboto-Black.ttf");
        m_paint = new Paint();
        m_paint.setAntiAlias(true);
        m_paint.setTypeface(robotoBlack);

        this.textSurface = textSurface;

        String[] ori = {" ", " ", " ", " ", " ", " "};
        setContent(ori);
    }
}
