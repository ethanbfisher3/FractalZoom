package UI;

import java.awt.Point;

import Main.ContentPane;

public enum Anchor {
	
	TOP_LEFT, TOP_CENTER, TOP_RIGHT,
	CENTER_LEFT, CENTER, CENTER_RIGHT,
	BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;
	
	public Point toPoint() {
		if (this == TOP_LEFT) return new Point();
		if (this == TOP_CENTER) return new Point(ContentPane.WIDTH / 2, 0);
		if (this == TOP_RIGHT) return new Point(ContentPane.WIDTH, 0);
		if (this == CENTER_LEFT) return new Point(0, ContentPane.HEIGHT / 2);
		if (this == CENTER) return new Point(ContentPane.WIDTH / 2, ContentPane.HEIGHT / 2);
		if (this == CENTER_RIGHT) return new Point(ContentPane.WIDTH, ContentPane.HEIGHT / 2);
		if (this == BOTTOM_LEFT) return new Point(0, ContentPane.HEIGHT);
		if (this == BOTTOM_CENTER) return new Point(ContentPane.WIDTH / 2, ContentPane.HEIGHT);
		return new Point(ContentPane.WIDTH, ContentPane.HEIGHT);
	}

}
