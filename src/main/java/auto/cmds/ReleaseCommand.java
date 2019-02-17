package auto.cmds;

import subsys.Release;

public class ReleaseCommand {

	//variables: constructor of release, double for power (should never be set to a high number), boolean of complete
		Release release;
		double power;
		boolean complete = false;
		
		

			
		//constructor of release uses power and the release
		public ReleaseCommand(Release r, double p) {
			// TODO Auto-generated constructor stub
			release = r;
			power = p;
			
		}
		
		//method execute that will release the cargo and change complete to true
		public void execute() {
			//release.setRelease(-1);
			complete = true;
		}
		
		//method to return complete
		public boolean isComplete() {
			return complete;
		}
		
		//stop
		public void stop() {
			//release.setRelease(0);
		}


}
