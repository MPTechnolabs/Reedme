// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.reedme.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

public class CustomTextInputLayout extends TextInputLayout
{

    private boolean a;
    private CharSequence b;

    public CustomTextInputLayout(Context context)
    {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutparams)
    {
        if (view instanceof EditText)
        {
            b = ((EditText)view).getHint();
        }
        super.addView(view, i, layoutparams);
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (!a)
        {
            setHint(null);
            canvas = (Canvas) getEditText().getHint();
            if (canvas != null)
            {
                b = (CharSequence) canvas;
            }
            setHint(b);
            a = true;
        }
    }
}
