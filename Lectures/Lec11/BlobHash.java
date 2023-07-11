package Lec11;
public class BlobHash extends Blob{
	
	@Override
	public boolean equals(Object otherBlob) {
		Blob b = (Blob)otherBlob; //cast as Blob
		if (x == b.x && y == b.y && r == b.r)
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		final int a=37;
		int sum = a * a * (int)x;
		sum += a * (int)y;
		sum += (int)r;
		return sum;
	}
}






