package xiangqi.studenthbnguyen;

import static xiangqi.common.XiangqiColor.*;
import static xiangqi.common.XiangqiPieceType.*;
import static org.junit.Assert.*;
import static xiangqi.common.MoveResult.*;
import org.junit.*;
import xiangqi.XiangqiGameFactory;
import xiangqi.common.*;
import static test.util.TestPiece.*;
import static test.util.TestCoordinate.*;

public class BetaXiangqiTestCases
{
	//
	// Test case code begins here
	//
	private XiangqiGame game;
	
	private static XiangqiCoordinate c11 = makeCoordinate(1, 1),
			c12 = makeCoordinate(1, 2), c13 = makeCoordinate(1, 3),
			c14 = makeCoordinate(1, 4), c15 = makeCoordinate(1, 5),
			c21 = makeCoordinate(2, 1), c22 = makeCoordinate(2, 2),
			c23 = makeCoordinate(2, 3), c24 = makeCoordinate(2, 4),
			c25 = makeCoordinate(2, 5), c31 = makeCoordinate(3, 1),
			c32 = makeCoordinate(3, 2), c33 = makeCoordinate(3, 3),
			c34 = makeCoordinate(3, 4), c35 = makeCoordinate(3, 5),
			c41 = makeCoordinate(4, 1), c42 = makeCoordinate(4, 2),
			c43 = makeCoordinate(4, 3), c44 = makeCoordinate(4, 4),
			c45 = makeCoordinate(4, 5), c51 = makeCoordinate(5, 1),
			c52 = makeCoordinate(5, 2), c53 = makeCoordinate(5, 3),
			c54 = makeCoordinate(5, 4), c55 = makeCoordinate(5, 5);

	private static XiangqiPiece noPiece = 
			makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE), 
			redChariot = makePiece(CHARIOT, RED),
			redAdvisor = makePiece(ADVISOR, RED),
			redGeneral = makePiece(GENERAL, RED),
			redSoldier = makePiece(SOLDIER, RED),
			blackChariot = makePiece(CHARIOT, BLACK),
			blackAdvisor = makePiece(ADVISOR, BLACK),
			blackGeneral = makePiece(GENERAL, BLACK),
			blackSoldier = makePiece(SOLDIER, BLACK);
	
	@Before
	public void setup()
	{
		game = XiangqiGameFactory.makeXiangqiGame(XiangqiGameVersion.BETA_XQ);
	}
	
	
	@Test
	public void correctInitialPositions()
	{
		assertEquals(redGeneral, game.getPieceAt(c13, RED));
		assertEquals(redChariot, game.getPieceAt(c11, RED));
		assertEquals(redChariot, game.getPieceAt(c15, RED));
		assertEquals(redAdvisor, game.getPieceAt(c12, RED));
		assertEquals(redAdvisor, game.getPieceAt(c14, RED));
		assertEquals(redSoldier, game.getPieceAt(c23, RED));
		assertEquals(blackGeneral, game.getPieceAt(c13, BLACK));
		assertEquals(blackChariot, game.getPieceAt(c11, BLACK));
		assertEquals(blackChariot, game.getPieceAt(c15, BLACK));
		assertEquals(blackAdvisor, game.getPieceAt(c12, BLACK));
		assertEquals(blackAdvisor, game.getPieceAt(c14, BLACK));
		assertEquals(blackSoldier, game.getPieceAt(c23, BLACK));
	}
	
	@Test
	public void queryAnEmptyLocation()
	{
		assertEquals(noPiece, game.getPieceAt(c22, BLACK));
	}
	
	@Test(expected = RuntimeException.class)
	public void queryAnInvalidLocation() {
		game.getPieceAt(makeCoordinate(100, 100), RED);
	}
	
	@Test 
	public void makeMoveOnEmptySource() {
		assertEquals(ILLEGAL, game.makeMove(c32, c33));
		assertTrue(game.getMoveMessage().length() > 5);		
	}
	
	@Test
	public void makeMoveWithInvalidCoordinates()
	{
		assertEquals(ILLEGAL, game.makeMove(makeCoordinate(0, 3), c14));
		assertEquals(ILLEGAL, game.makeMove(c11, makeCoordinate(1, 6)));
	}
	
	@Test
	public void makeValidChariotMove()
	{
		assertEquals(OK, game.makeMove(c11, c21));
		assertEquals(redChariot, game.getPieceAt(c21, RED));
		assertEquals(noPiece, game.getPieceAt(c11, RED));
	}

	@Test
	public void makeInvalidChariotMove() {
		assertEquals(OK, game.makeMove(c11, c21));
		assertEquals(ILLEGAL, game.makeMove(c11, c22));
	}
	
	@Test
	public void attemptToMoveOpponentPiece()
	{
		assertEquals(ILLEGAL, game.makeMove(c51, c41));
	}
	
	@Test
	public void attemptToCaptureOwnPiece()
	{
		assertEquals(ILLEGAL, game.makeMove(c11, c12));
	}
	
	@Test
	public void ensureMessageOnIllegalMove()
	{
		game.makeMove(c11, c12);
		assertTrue(game.getMoveMessage().length() > 5);		// Minimum of 6 characters seems reasonable
	}
	

	@Test
	public void makeValidMoveForEachPlayer()
	{
		game.makeMove(c11, c31);
		assertEquals(redChariot, game.getPieceAt(c31, RED));
		assertEquals(OK, game.makeMove(c15, c25));
		assertEquals(blackChariot, game.getPieceAt(c25, BLACK));
	}
	
	@Test
	public void validAdvisorMove()
	{
		assertEquals(OK, game.makeMove(c12, c21));
		assertEquals(redAdvisor, game.getPieceAt(c21, RED));
	}
	
	@Test
	public void invalidAdvisorMove()
	{
		assertEquals(ILLEGAL, game.makeMove(c12, c22));
	}
	
	@Test
	public void validSoldierMove()
	{
		assertEquals(OK, game.makeMove(c23, c33));
		assertEquals(redSoldier, game.getPieceAt(c33, RED));
	}
	
	@Test
	public void invalidSoldierMove()
	{
		assertEquals(ILLEGAL, game.makeMove(c23, c22));
	}
	
	@Test 
	public void redSoldierCannotMoveBackwards() {
		assertEquals(ILLEGAL, game.makeMove(c23, c13));
	}
	
	@Test 
	public void blackSoldierCannotMoveBackwards() {
		assertEquals(OK, game.makeMove(c11, c21));
		assertEquals(ILLEGAL, game.makeMove(c23, c13));
	}
	
	@Test
	public void soldierCapturesSoldier()
	{
		game.makeMove(c23, c33);
		assertEquals(OK, game.makeMove(c23,  c33));
		assertEquals(blackSoldier, game.getPieceAt(c33, BLACK));
	}
	
	@Test
	public void validGeneralMove()
	{
		game.makeMove(c12, c21);
		game.makeMove(c11, c21);
		assertEquals(OK, game.makeMove(c13, c12));
		assertEquals(redGeneral, game.getPieceAt(c12, RED));
	}
	
	@Test
	public void invalidGeneralMove()
	{
		assertEquals(ILLEGAL, game.makeMove(c13, c22));
	}
	/*
	@Test 
	public void blackPutsGeneralInCheck() {
		game.makeMove(c11, c51);
		assertEquals(redChariot, game.getPieceAt(c51, RED));
		assertEquals(ILLEGAL, game.makeMove(c14, c25));
	}
	
	@Test
	public void redAdvisorMovesButBlocked() {
		assertEquals(ILLEGAL, game.makeMove(c12, c43));
	}
	
	@Test 
	public void numberOfMovesExceeded() {
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c51, c11));
		assertEquals(OK, game.makeMove(c51, c11));
		
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c51, c11));
		assertEquals(OK, game.makeMove(c51, c11));
		
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c51, c11));
		assertEquals(OK, game.makeMove(c51, c11));
		
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c51, c11));
		assertEquals(OK, game.makeMove(c51, c11));
		
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c11, c51));
		assertEquals(OK, game.makeMove(c51, c11));
		assertEquals(OK, game.makeMove(c51, c11));
		
		assertEquals(DRAW, game.makeMove(c11, c51)); 
	}
	
	@Test 
	public void checkNotRemoved() {
		assertEquals(OK, game.makeMove(c23, c33));
		assertEquals(OK, game.makeMove(c11, c31));
		assertEquals(OK, game.makeMove(c15, c25));
		assertEquals(OK, game.makeMove(c31, c33));
		assertEquals(ILLEGAL, game.makeMove(c31, c41));
	}
	
	@Test 
	public void checkRemovedByBlocking() {
		assertEquals(OK, game.makeMove(c23, c33));
		assertEquals(OK, game.makeMove(c11, c31));
		assertEquals(OK, game.makeMove(c15, c25));
		assertEquals(OK, game.makeMove(c31, c33));
		assertEquals(OK, game.makeMove(c14, c23));
	}
	
	@Test
	public void checkRemovedByCapturingTheAttacker() {
		assertEquals(OK, game.makeMove(c11, c21));
		assertEquals(OK, game.makeMove(c11, c41));
		assertEquals(OK, game.makeMove(c21, c31));
		assertEquals(OK, game.makeMove(c41, c43));
		assertEquals(OK, game.makeMove(c12, c23));
	}
	
	@Test 
	public void checkRemovedByMovingGeneral() {
		assertEquals(OK, game.makeMove(c23, c33));
		assertEquals(OK, game.makeMove(c23, c33));
		assertEquals(OK, game.makeMove(c12, c21));
		assertEquals(OK, game.makeMove(c33, c43));
		assertEquals(OK, game.makeMove(c13, c12));
	}
	
	@Test 
	public void generalPutsItselfInCheck() {
		assertEquals(OK, game.makeMove(c23, c33));
		assertEquals(OK, game.makeMove(c23, c33));
		assertEquals(ILLEGAL, game.makeMove(c13, c23));
	}
	*/
}