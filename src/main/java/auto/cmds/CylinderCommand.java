package auto.cmds;

import subsys.Release;

public class CylinderCommand {

	//variables: release, boolean to replace low, boolean of complete
		Release release;
		boolean complete = false;
		
		//constructor of the cylinder command
		public CylinderCommand(Release r) {
			// TODO Auto-generated constructor stub
			release = r;
			
		}

		//method to execute the change of the first boolean and change complete to true for that action
			
		
		//method to check if complete is true or not
		public boolean isComplete() {
			return complete;
		}
			
		//stop
		public void stop() {
			
		}


}
