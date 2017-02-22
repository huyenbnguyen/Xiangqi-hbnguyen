/**
 * 
 */
package xiangqi.studenthbnguyen;

import static xiangqi.common.XiangqiColor.*;
import static xiangqi.common.XiangqiPieceType.*;
import static org.junit.Assert.*;
import static xiangqi.common.MoveResult.*;
import org.junit.*;

import test.util.TestCoordinate;
import test.util.TestPiece;
import xiangqi.XiangqiGameFactory;
import xiangqi.common.*;

/**
 * @author huyennguyen
 *
 */
public class GammaXiangqiTestCases {
	private XiangqiGame game;
	private static XiangqiPiece redChariot = makePiece(CHARIOT, RED),
			redElephant = makePiece(ELEPHANT, RED),
			redAdvisor = makePiece(ADVISOR, RED),
			redGeneral = makePiece(GENERAL, RED),
			redSoldier = makePiece(SOLDIER, RED),
			blackChariot = makePiece(CHARIOT, BLACK),
			blackElephant = makePiece(ELEPHANT, BLACK),
			blackAdvisor = makePiece(ADVISOR, BLACK),
			blackGeneral = makePiece(GENERAL, BLACK),
			blackSoldier = makePiece(SOLDIER, BLACK),
			noPiece = makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE); 

	private static XiangqiCoordinate c1_1 = makeCoordinate(1, 1),
			c1_2 = makeCoordinate(1, 2), c1_3 = makeCoordinate(1, 3),
			c1_4 = makeCoordinate(1, 4), c1_5 = makeCoordinate(1, 5),
			c1_6 = makeCoordinate(1, 6), c1_7 = makeCoordinate(1, 7),
			c1_8 = makeCoordinate(1, 8), c1_9 = makeCoordinate(1, 9),
			c2_1 = makeCoordinate(2, 1), c2_2 = makeCoordinate(2, 2),
			c2_3 = makeCoordinate(2, 3), c2_4 = makeCoordinate(2, 4),
			c2_5 = makeCoordinate(2, 5), c2_6 = makeCoordinate(2, 6),
			c2_7 = makeCoordinate(2, 7), c2_8 = makeCoordinate(2, 8),
			c2_9 = makeCoordinate(2, 9), c3_1 = makeCoordinate(3, 1),
			c3_2 = makeCoordinate(3, 2), c3_3 = makeCoordinate(3, 3),
			c3_4 = makeCoordinate(3, 4), c3_5 = makeCoordinate(3, 5),
			c3_6 = makeCoordinate(3, 6), c3_7 = makeCoordinate(3, 7),
			c3_8 = makeCoordinate(3, 8), c3_9 = makeCoordinate(3, 9),
			c4_1 = makeCoordinate(4, 1), c4_2 = makeCoordinate(4, 2),
			c4_3 = makeCoordinate(4, 3), c4_4 = makeCoordinate(4, 4),
			c4_5 = makeCoordinate(4, 5), c4_6 = makeCoordinate(4, 6),
			c4_7 = makeCoordinate(4, 7), c4_8 = makeCoordinate(4, 8),
			c4_9 = makeCoordinate(4, 9), c5_1 = makeCoordinate(5, 1),
			c5_2 = makeCoordinate(5, 2), c5_3 = makeCoordinate(5, 3),
			c5_4 = makeCoordinate(5, 4), c5_5 = makeCoordinate(5, 5),
			c5_6 = makeCoordinate(5, 6), c5_7 = makeCoordinate(5, 7),
			c5_8 = makeCoordinate(5, 8), c5_9 = makeCoordinate(5, 9),
			c6_1 = makeCoordinate(6, 1), c6_2 = makeCoordinate(6, 2),
			c6_3 = makeCoordinate(6, 3), c6_4 = makeCoordinate(6, 4),
			c6_5 = makeCoordinate(6, 5), c6_6 = makeCoordinate(6, 6),
			c6_7 = makeCoordinate(6, 7), c6_8 = makeCoordinate(6, 8),
			c6_9 = makeCoordinate(6, 9), c7_1 = makeCoordinate(7, 1),
			c7_2 = makeCoordinate(7, 2), c7_3 = makeCoordinate(7, 3),
			c7_4 = makeCoordinate(7, 4), c7_5 = makeCoordinate(7, 5),
			c7_6 = makeCoordinate(7, 6), c7_7 = makeCoordinate(7, 7),
			c7_8 = makeCoordinate(7, 8), c7_9 = makeCoordinate(7, 9),
			c8_1 = makeCoordinate(8, 1), c8_2 = makeCoordinate(8, 2),
			c8_3 = makeCoordinate(8, 3), c8_4 = makeCoordinate(8, 4),
			c8_5 = makeCoordinate(8, 5), c8_6 = makeCoordinate(8, 6),
			c8_7 = makeCoordinate(8, 7), c8_8 = makeCoordinate(8, 8),
			c8_9 = makeCoordinate(8, 9), c9_1 = makeCoordinate(9, 1),
			c9_2 = makeCoordinate(9, 2), c9_3 = makeCoordinate(9, 3),
			c9_4 = makeCoordinate(9, 4), c9_5 = makeCoordinate(9, 5),
			c9_6 = makeCoordinate(9, 6), c9_7 = makeCoordinate(9, 7),
			c9_8 = makeCoordinate(9, 8), c9_9 = makeCoordinate(9, 9),
			c10_1 = makeCoordinate(10, 1), c10_2 = makeCoordinate(10, 2),
			c10_3 = makeCoordinate(10, 3), c10_4 = makeCoordinate(10, 4),
			c10_5 = makeCoordinate(10, 5), c10_6 = makeCoordinate(10, 6),
			c10_7 = makeCoordinate(10, 7), c10_8 = makeCoordinate(10, 8),
			c10_9 = makeCoordinate(10, 9);

	@Before
	public void setup()
	{
		game = XiangqiGameFactory.makeXiangqiGame(XiangqiGameVersion.GAMMA_XQ);
	}
	
	@Test
	public void correctInitialPositions()
	{
		assertEquals(redGeneral, game.getPieceAt(c1_5, RED));
		assertEquals(redChariot, game.getPieceAt(c1_1, RED));
		assertEquals(redChariot, game.getPieceAt(c1_9, RED));
		assertEquals(redAdvisor, game.getPieceAt(c1_4, RED));
		assertEquals(redAdvisor, game.getPieceAt(c1_6, RED));
		assertEquals(redElephant, game.getPieceAt(c1_3, RED));
		assertEquals(redElephant, game.getPieceAt(c1_7, RED));
		assertEquals(redSoldier, game.getPieceAt(c4_1, RED));
		assertEquals(redSoldier, game.getPieceAt(c4_3, RED));
		assertEquals(redSoldier, game.getPieceAt(c4_5, RED));
		assertEquals(redSoldier, game.getPieceAt(c4_7, RED));
		assertEquals(redSoldier, game.getPieceAt(c4_9, RED));
		assertEquals(blackChariot, game.getPieceAt(c1_1, BLACK));
		assertEquals(blackChariot, game.getPieceAt(c1_9, BLACK));
		assertEquals(blackAdvisor, game.getPieceAt(c1_4, BLACK));
		assertEquals(blackAdvisor, game.getPieceAt(c1_6, BLACK));
		assertEquals(blackElephant, game.getPieceAt(c1_3, BLACK));
		assertEquals(blackElephant, game.getPieceAt(c1_7, BLACK));
		assertEquals(blackSoldier, game.getPieceAt(c4_1, BLACK));
		assertEquals(blackSoldier, game.getPieceAt(c4_3, BLACK));
		assertEquals(blackSoldier, game.getPieceAt(c4_5, BLACK));
		assertEquals(blackSoldier, game.getPieceAt(c4_7, BLACK));
		assertEquals(blackSoldier, game.getPieceAt(c4_9, BLACK));
	}

	@Test
	public void queryAnEmptyLocation()
	{
		assertEquals(noPiece, game.getPieceAt(c2_2, BLACK));
	}
	
	@Test(expected = RuntimeException.class)
	public void queryAnInvalidLocation() {
		game.getPieceAt(makeCoordinate(100, 100), RED);
	}
	
	@Test 
	public void makeMoveOnEmptySource() {
		assertEquals(ILLEGAL, game.makeMove(c2_2, c3_3));
		assertTrue(game.getMoveMessage().length() > 5);		
	}
	
	@Test
	public void makeMoveWithInvalidCoordinates()
	{
		assertEquals(ILLEGAL, game.makeMove(makeCoordinate(0, 3), c1_4));
		assertEquals(ILLEGAL, game.makeMove(c1_1, makeCoordinate(1, 15)));
	}
	
	@Test
	public void makeValidChariotMove()
	{
		assertEquals(OK, game.makeMove(c1_1, c2_1));
		assertEquals(redChariot, game.getPieceAt(c2_1, RED));
		assertEquals(noPiece, game.getPieceAt(c1_1, RED));
	}
	
	// Helper methods
	private static XiangqiCoordinate makeCoordinate(int rank, int file)
	{
		return new TestCoordinate(rank, file);  
	}

	public static XiangqiPiece makePiece(XiangqiPieceType pieceType, XiangqiColor color)
	{
		return new TestPiece(pieceType, color);
	}
}
