package su.levenetc.android.textsurface.sample.checks;

import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import su.levenetc.android.textsurface.animations.Scale;
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

    private static int history_num = 5;
    private Text[] m_history2 = new Text[history_num];
    private Paint m_paint;
    private int m_to_disappear = 0;

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
        int mid_pos = history_num / 2;
        int i;
        /*history[mid_pos] = createTextNode(" ", 20, 0x444444, Align.SURFACE_CENTER, null);
        for (i = mid_pos - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                history[i] = createTextNode(content_string[i / 2], 46 + 2 * i, 0x999999 - 0x111111 * i,
                        Align.TOP_OF | Align.CENTER_OF, history[i + 1]);
            }
            else {
                history[i] = createTextNode(" ", 20, 0x999999 - 0x111111 * i,
                        Align.TOP_OF | Align.CENTER_OF, history[i + 1]);
            }
        }
        for (i = mid_pos + 1; i < history_num; i++) {
            if (i % 2 == 0) {
                history[i] = createTextNode(content_string[i / 2], 46 + 2 * i, 0x999999 - 0x111111 * i,
                        Align.BOTTOM_OF | Align.CENTER_OF, history[i - 1]);
            }
            else {
                history[i] = createTextNode(" ", 20, 0x999999 - 0x111111 * i,
                        Align.BOTTOM_OF | Align.CENTER_OF, history[i - 1]);
            }
        }*/

        history[0] = createTextNode(content_string[0], 20, 0x444444, Align.SURFACE_CENTER, null);
        for (i = 1; i < history_num; i++) {
            history[i] = createTextNode(content_string[i], 46 + 2 * i, 0x999999 - 0x111111 * i,
                    Align.BOTTOM_OF | Align.CENTER_OF, history[i - 1]);
        }
    }

    public void setContent(String[] content_string) {
        setArray(content_string, m_history2);
    }

    public void refreshUI(TextSurface textSurface, String new_content) {
        /*textSurface.play(
                new Parallel(
                        Alpha.hide(m_history2[m_to_disappear], 100),
                        Alpha.hide(m_history2[m_to_disappear + 1], 100)
                )
        );
        int last_pos = (m_to_disappear + 9) % 10;
        Log.e("dis", m_history2[m_to_disappear].getIndex() + "");
        Log.e("dis+1", m_history2[m_to_disappear + 1].getIndex() + "");
        m_history2[m_to_disappear] = createTextNode(new_content, 62, 0xffffff,
                Align.BOTTOM_OF | Align.CENTER_OF, m_history2[last_pos]);
        m_history2[m_to_disappear + 1] = createTextNode(" ", 20, 0x999999,
                Align.BOTTOM_OF | Align.CENTER_OF, m_history2[m_to_disappear]);
        textSurface.play(
                new Parallel(
                        ShapeReveal.create(m_history2[m_to_disappear], 500, SideCut.show(Side.LEFT), false),
                        //ShapeReveal.create(m_history2[m_to_disappear + 1], 500, SideCut.show(Side.LEFT), false),
                        ChangeColor.to(m_history2[(m_to_disappear + 8) % 10], 500, 0xcccccc),
                        new Scale(m_history2[(m_to_disappear + 8) % 10], 500, 1f, (float) (58.0 / 62.0), Pivot.LEFT),
                        ChangeColor.to(m_history2[(m_to_disappear + 6) % 10], 500, 0x999999),
                        new Scale(m_history2[(m_to_disappear + 6) % 10], 500, (float) (58.0 / 62.0), (float) (54.0 / 62.0), Pivot.LEFT),
                        ChangeColor.to(m_history2[(m_to_disappear + 4) % 10], 500, 0x666666),
                        new Scale(m_history2[(m_to_disappear + 4) % 10], 500, (float) (54.0 / 62.0), (float) (50.0 / 62.0), Pivot.LEFT),
                        ChangeColor.to(m_history2[(m_to_disappear + 2) % 10], 500, 0x333333),
                        new Scale(m_history2[(m_to_disappear + 2) % 10], 500, (float) (50.0 / 62.0), (float) (46.0 / 62.0), Pivot.LEFT),
                        TransSurface.toCenter(m_history2[(m_to_disappear + 7) % 10], 500)
                )
        );
        m_to_disappear = (m_to_disappear + 2) % 10;*/

        // no space
        textSurface.play(
                new Parallel(
                        Alpha.hide(m_history2[m_to_disappear], 100)
                )
        );
        int last_pos = (m_to_disappear + 4) % 5;
        m_history2[m_to_disappear] = createTextNode(new_content, 62, 0xffffff,
                Align.BOTTOM_OF | Align.CENTER_OF, m_history2[last_pos]);
        textSurface.play(
                new Parallel(
                        ShapeReveal.create(m_history2[m_to_disappear], 500, SideCut.show(Side.LEFT), false),
                        ChangeColor.to(m_history2[(m_to_disappear + 4) % 5], 500, 0xcccccc),
                        new Scale(m_history2[(m_to_disappear + 4) % 5], 500, 1f, (float) (58.0 / 62.0), Pivot.LEFT),
                        ChangeColor.to(m_history2[(m_to_disappear + 3) % 5], 500, 0x999999),
                        new Scale(m_history2[(m_to_disappear + 3) % 5], 500, (float) (58.0 / 62.0), (float) (54.0 / 62.0), Pivot.LEFT),
                        ChangeColor.to(m_history2[(m_to_disappear + 2) % 5], 500, 0x666666),
                        new Scale(m_history2[(m_to_disappear + 2) % 5], 500, (float) (54.0 / 62.0), (float) (50.0 / 62.0), Pivot.LEFT),
                        ChangeColor.to(m_history2[(m_to_disappear + 1) % 5], 500, 0x333333),
                        new Scale(m_history2[(m_to_disappear + 1) % 5], 500, (float) (50.0 / 62.0), (float) (46.0 / 62.0), Pivot.LEFT),
                        TransSurface.toCenter(m_history2[(m_to_disappear + 3) % 5], 500)
                )
        );
        m_to_disappear = (m_to_disappear + 1) % 5;
    }

    public CookieThumperSample(AssetManager assetManager) {
        final Typeface robotoBlack = Typeface.createFromAsset(assetManager, "fonts/Roboto-Black.ttf");
        m_paint = new Paint();
        m_paint.setAntiAlias(true);
        m_paint.setTypeface(robotoBlack);

        String[] ori = {" ", " ", " ", " ", " "};
        setContent(ori);
    }
}
