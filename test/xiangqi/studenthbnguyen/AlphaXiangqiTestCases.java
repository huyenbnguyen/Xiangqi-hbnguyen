/**
 * 
 */
package xiangqi.studenthbnguyen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import test.util.*;
import xiangqi.XiangqiGameFactory;
import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiGameVersion;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;

/**
 * @author huyennguyen
 *
 */
public class AlphaXiangqiTestCases {
private XiangqiGame game;
	
	@Before
	public void setup() {
		game = XiangqiGameFactory.makeXiangqiGame(XiangqiGameVersion.ALPHA_XQ);
	}
	
	@Test
	public void factoryProducesAlphaXiangqiGame() {
		assertNotNull(game);
	}
	
	@Test
	public void redMakesValidFirstMove() {
		assertEquals(MoveResult.OK, game.makeMove(
				TestCoordinate.makeCoordinate(1, 1),
				TestCoordinate.makeCoordinate(1, 2))); 
	}
	
	@Test
	public void redMakesInvalidFirstMove() {
		assertEquals(MoveResult.ILLEGAL, game.makeMove(
				TestCoordinate.makeCoordinate(1, 1),
				TestCoordinate.makeCoordinate(2, 1)));
		assertTrue(game.getMoveMessage().length() >= 1); 
	}
	 
	@Test
	public void blackMakesValidSecondMove() {
		game.makeMove(TestCoordinate.makeCoordinate(1, 1),
				TestCoordinate.makeCoordinate(1, 2));
		assertEquals(MoveResult.RED_WINS, game.makeMove(
				TestCoordinate.makeCoordinate(1, 1),
				TestCoordinate.makeCoordinate(1, 2)));
	}
	
	@Test
	public void blackMakesInvalidSecondMove() {
		game.makeMove(TestCoordinate.makeCoordinate(1, 1),
				TestCoordinate.makeCoordinate(1, 2));
		assertEquals(MoveResult.ILLEGAL, game.makeMove(
				TestCoordinate.makeCoordinate(1, 1),
				TestCoordinate.makeCoordinate(2, 1)));
	}
	
	
	
	@Test 
	public void tryToMoveFromInvalidLocation() {
		assertEquals(MoveResult.ILLEGAL, game.makeMove(
				TestCoordinate.makeCoordinate(2, 1),
				TestCoordinate.makeCoordinate(1, 2)));
		assertTrue(game.getMoveMessage().length() >= 1);
	}
	
	@Test 
	public void getPieceAtReturnsNoneNone() {
		final XiangqiPiece p = game.getPieceAt(TestCoordinate.makeCoordinate(1, 1), XiangqiColor.RED);
		assertEquals(XiangqiPieceType.NONE, p.getPieceType());
		assertEquals(XiangqiColor.NONE, p.getColor());
	}
	
//	@Test(expected = RuntimeException.class) 
//	public void test() {
//		throw new RuntimeException();
//	}

}
