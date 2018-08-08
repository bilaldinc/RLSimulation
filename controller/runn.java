package controller;

import view.Controller;

/**
 * Created by bilal on 25.06.2016.
 */
public class runn implements Runnable {
	int stepsize;
	Controller asd;
	double prosses;
	public runn(int stepsize,Controller asd,double prosses){
		this.asd = asd;
		this.stepsize = stepsize;
		this.prosses = prosses;
	}
	@Override
	public void run() {
		asd.history.add(stepsize);
		asd.history2.add(asd.history2.get(asd.history2.size()-1)+1);
		asd.progress.setProgress(prosses);
	}
}
