package com.shopov.cardsapp;

import java.util.EnumMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.shopov.cardsapp.model.Card;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public final class CardFragment extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Content";
	private String mContent = "CARD FRAGMENT";
	
	private Card card = new Card();
	
	private ImageView qrImageView = null;
	private TextView expiryTextView = null;
	private TextView nameTextView = null;
	private LinearLayout nameLinearLayout = null;
	private LinearLayout expiryLinearLayout = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }
    
	@Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.card_front_custom_view, container, false);
		nameLinearLayout = (LinearLayout) v.findViewById(R.id.name_linear_layout);
		expiryLinearLayout = (LinearLayout) v.findViewById(R.id.expiry_linear_layout);
		qrImageView = (ImageView) v.findViewById(R.id.qr_image);
		expiryTextView = (TextView) expiryLinearLayout.findViewById(R.id.expiry_textView);
		nameTextView = (TextView) nameLinearLayout.findViewById(R.id.name_textView);
		
		
		v.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CardBackFragment cardBackFragment = new CardBackFragment();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.card_front_relativeLayout, cardBackFragment);
				transaction.commit();
			}
		});
		
		//TODO if statement should check if card is valid
		// else should set textviews for expired card 
		if (true && card != null) {
			
		}
		
		String qrData = "http://www.google.com/";
		
		Bitmap bitmap = null;
		try {
			bitmap = encodeAsBitmap(qrData, BarcodeFormat.CODE_128, 150, 50);
			qrImageView.setImageBitmap(bitmap);
		} catch (WriterException e) {
	        e.printStackTrace();
	    }

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
    
    private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width,
			int img_height) throws WriterException {
		String contentsToEncode = contents;
		if (contentsToEncode == null) {
			return null;
		}
		Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
		hints.put(EncodeHintType.MARGIN, 1); //removing border could cause problems with scanning
											//default value is 4
		String encoding = guessAppropriateEncoding(contentsToEncode);
		if (encoding != null) {
			hints.put(EncodeHintType.CHARACTER_SET, encoding);
		}
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result;
		try {
			result = writer.encode(contentsToEncode, format, img_width,
					img_height, hints);
		} catch (IllegalArgumentException iae) {
			// Unsupported format
			return null;
		}
		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	private static String guessAppropriateEncoding(CharSequence contents) {
		// Very crude at the moment
		for (int i = 0; i < contents.length(); i++) {
			if (contents.charAt(i) > 0xFF) {
				return "UTF-8";
			}
		}
		return null;
	}
	
}
