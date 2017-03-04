/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016-2017 Gary F. Pollice
 *******************************************************************************/

package xiangqi;

import xiangqi.common.*;
import static xiangqi.common.XiangqiGameVersion.*;
import xiangqi.studenthbnguyen.common.XiangqiBaseGame;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validatorchecker.PieceChecker;
import xiangqi.studenthbnguyen.validatorchecker.PostMoveChecker;
import xiangqi.studenthbnguyen.validatorchecker.PreMoveChecker;
import xiangqi.studenthbnguyen.versions.alphaxiangqi.AlphaXiangqi;
import xiangqi.studenthbnguyen.versions.otherxiangqiversions.BetaInitializer;
import xiangqi.studenthbnguyen.versions.otherxiangqiversions.DeltaInitializer;
import xiangqi.studenthbnguyen.versions.otherxiangqiversions.GammaInitializer;
import xiangqi.studenthbnguyen.versions.otherxiangqiversions.InitializerTemplate;

/**
 * A simple factory object that creates the appropriate instance of a XiangqiGame.
 * @version Dec 26, 2016
 */
public class XiangqiGameFactory
{
	private static XiangqiBaseGame game = null;
	private static XiangqiState state = null;
	private static InitializerTemplate initializer;
	
	/**
	 * Factory method that returns an instance of the requested game.
	 * @param version the version requested
	 * @return the instance of the requested game
	 */
	public static XiangqiGame makeXiangqiGame(XiangqiGameVersion version)
	{
		
		if (version == ALPHA_XQ) {
			return new AlphaXiangqi();
		} else {
			chooseInitialzer(version);
			state = initializer.getState();
			game = new XiangqiBaseGame(state);
			setCheckers(initializer);
		}
		return (XiangqiGame) game;
	}
	
	private static void chooseInitialzer(XiangqiGameVersion version) {
		switch (version) {
		case BETA_XQ:
			initializer = new BetaInitializer();
			break;
		case GAMMA_XQ:
			initializer = new GammaInitializer();
			break;
		case DELTA_XQ:
			initializer = new DeltaInitializer();
			break;
		default:
			System.out.println("Not yet implemented");
			break;
		}
	}
	
	private static void setCheckers(InitializerTemplate init) {
		PieceChecker.setPiecevalidators(init.getPieceValidators());
		PostMoveChecker.setPostMoveValidators(init.getPostMoveValidators());
		PreMoveChecker.setPreMoveValidators(init.getPreMoveValidators());
	}
}
