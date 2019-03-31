package project.as224qc.dv606.slcommuter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import project.as224qc.dv606.slcommuter.R;

public class EmptyStateView extends ConstraintLayout {

    private ImageView imageView;
    private TextView textView;

    public EmptyStateView(Context context) {
        this(context,null);
    }

    public EmptyStateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyStateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.layout_empty_state,this);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textview);

        if(attrs != null){
            final TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.EmptyStateView,
                    0, 0);

            Drawable image;
            String emptyStateText;

            try {
                image = a.getDrawable(R.styleable.EmptyStateView_image);
                emptyStateText = a.getString(R.styleable.EmptyStateView_title);
            } finally {
                a.recycle();
            }

            if(image != null){
                imageView.setImageDrawable(image);
            }
            textView.setText(emptyStateText);
        }
    }

}
