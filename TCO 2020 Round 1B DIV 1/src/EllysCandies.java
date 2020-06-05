import java.util.*;
import java.math.*;
import static java.lang.Math.*;

public class EllysCandies {
	
	public String getWinner(int[] boxes) {
		return boxes.length % 2 == 1 ? "Elly" : "Kris";
	}
}
