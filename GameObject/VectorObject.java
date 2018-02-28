package GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import Helper.Utility;
import Vector.Matrix3x3f;
import Vector.Vector2f;

public class VectorObject implements Drawable {

	Vector2f[] poly;
	Matrix3x3f worldMat;
	Matrix3x3f viewport;
	
	protected Vector2f location;
	protected Color color;
	protected float rotation;
	protected float scale;

	@Override
	public void updateWorld() {
		worldMat = Matrix3x3f.identity();			
		worldMat = worldMat.mul(Matrix3x3f.rotate(rotation));
		worldMat = worldMat.mul(Matrix3x3f.scale(scale, scale));
		worldMat = worldMat.mul(Matrix3x3f.translate(location));
	}

	@Override
	public void render(Graphics g) {
		Vector2f[] worldPoly = new Vector2f[poly.length];
		for(int i = 0; i < poly.length; i++)
		{
			worldPoly[i] = new Vector2f(worldMat.mul(poly[i]));
		}
		
		for(int i = 0; i < poly.length; i++)
		{
			worldPoly[i] = new Vector2f(viewport.mul(worldPoly[i]));
		}
		
		Utility.drawPolygon(g, worldPoly, color);
	}
	
	public void setViewport(Matrix3x3f viewport)
	{
		this.viewport = viewport;
	}
	
	public float getRotation()
	{
		return rotation;
	}
	
	public void setRotation(float rot)
	{
		this.rotation = rot;
	}
	
	public Matrix3x3f getMatrix()
	{
		return worldMat;
	}
	
	public void setScale(float sc)
	{
		this.scale = sc;
	}
	
	public Vector2f getLocation()
	{
		return location;
	}
	
	public void setLocation(Vector2f loc)
	{
		location = loc;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
}
