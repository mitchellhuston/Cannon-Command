package Helper;

import java.awt.Color;
import java.awt.Graphics;

import Vector.Matrix3x3f;
import Vector.Vector2f;

public class Utility {
	public static Matrix3x3f createViewport(float worldWidth, float worldHeight, float screenWidth,
			float screenHeight) {
		float sx = (screenWidth - 1) / worldWidth;
		float sy = (screenHeight - 1) / worldHeight;
		float tx = (screenWidth - 1) / 2.0f;
		float ty = (screenHeight - 1) / 2.0f;
		Matrix3x3f viewport = Matrix3x3f.scale(sx, -sy);
		viewport = viewport.mul(Matrix3x3f.translate(tx, ty));
		return viewport;
	}

	public static Matrix3x3f createReverseViewport(float worldWidth, float worldHeight,

			float screenWidth, float screenHeight) {
		float sx = worldWidth / (screenWidth - 1);
		float sy = worldHeight / (screenHeight - 1);
		float tx = (screenWidth - 1) / 2.0f;
		float ty = (screenHeight - 1) / 2.0f;
		Matrix3x3f viewport = Matrix3x3f.translate(-tx, -ty);
		viewport = viewport.mul(Matrix3x3f.scale(sx, -sy));
		return viewport;
	}

	public static void drawPolygon(Graphics g, Vector2f[] polygon, Color color) {
		g.setColor(color);

		Vector2f p;
		Vector2f s = polygon[polygon.length - 1];

		for (int i = 0; i < polygon.length; ++i) {
			p = polygon[i];
			g.drawLine((int) s.x, (int) s.y, (int) p.x, (int) p.y);
			s = p;
		}
	}
}
