
public class CommandExecution {
	
	
	 /* >>> EXECUTION <<< */
	
	//Most important routine of Command execution! (does an DECODE-call to CommandInterpreter)
    static void execute(int command)
	  {
    	
	   int[] opcAndArguments = CommandInterpreter.decode(command);
	   
	   Pic16F84Registers.incrementPC(); // inkrementing PC as part of the execution!
	   
	   
	   //Chooses instruction from list below based on content of opcAndArguments[0] which holds the OPC	      
	   switch(opcAndArguments[0])
	     { 	
	   
	                 /* >-----------------------------------------------------------------------< */
                     /* >----------------Byte-oriented File Register Operations-----------------< */
                     /* >-----------------------------------------------------------------------< */
	   
	      case 0x0700: // ADDWF (W or File Register(d-Bit) = W + File Register)    
			  System.out.println("ADDWF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {
	    		  // Fetch value directly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());

	    		  //Execute Operation (Addition)
	  		      checkRegisterForDigitCarry(value, Pic16F84Registers.W_REGISTER, "Addition");
	  		      checkForOverflow(value, Pic16F84Registers.W_REGISTER, "Addition"); 
    			  checkRegisterForZero((byte)(value + Pic16F84Registers.W_REGISTER));
	  			
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
		  			  System.out.println("ADDWF: " + String.format("%2X", opcAndArguments[2]) + "h" + " + " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W");	
	    			  Pic16F84Registers.W_REGISTER = (byte)(value + Pic16F84Registers.W_REGISTER);
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
		  			  System.out.println("ADDWF: " + String.format("%2X", opcAndArguments[2]) + "h" + " + " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to File Register");	
	    			  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)(value + Pic16F84Registers.W_REGISTER));
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch value indirectly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  checkRegisterForZero(value); 
	    		  
	    		  //Execute Operation (Addition)
	  		      checkRegisterForDigitCarry(value, Pic16F84Registers.W_REGISTER, "Addition");
	  		      checkForOverflow(value, Pic16F84Registers.W_REGISTER, "Addition"); 
    			  checkRegisterForZero((byte)(value + Pic16F84Registers.W_REGISTER));
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
		  			  System.out.println("ADDWF: " + String.format("%2X", opcAndArguments[2]) + "h" + " + " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W");	
	    			  Pic16F84Registers.W_REGISTER = (byte)(value + Pic16F84Registers.W_REGISTER);
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
		  			  System.out.println("ADDWF: " + String.format("%2X", opcAndArguments[2]) + "h" + " + " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to File Register");	
	    			  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)(value + Pic16F84Registers.W_REGISTER));
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  break;
	    	  
	    	  
	    	  /* >-----------------------------------------------------------------------< */
	    	  
	    	  
	      case 0x0500: // ANDWF (W or File Register(d-Bit) = W & File Register)    
	    	  System.out.println("ANDWF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {
	    		  // Fetch value directly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
	    		  
	    		  // Execute operation (And)
	 			  checkRegisterForZero((byte)(Pic16F84Registers.W_REGISTER & value));
	  			
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
		  			  System.out.println("ANDWF: " + String.format("%2X", value) + "h" + " & " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W");	
	    			  Pic16F84Registers.W_REGISTER = (byte)(Pic16F84Registers.W_REGISTER & value);;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
		  			  System.out.println("ANDWF: " + String.format("%2X", value) + "h" + " & " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to File Register");	
	    			  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)(Pic16F84Registers.W_REGISTER & value));
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    		 
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch value indirectly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  // Execute operation (And)
	 			  checkRegisterForZero((byte)(Pic16F84Registers.W_REGISTER & value));
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
		  			  System.out.println("ANDWF: " + String.format("%2X", value) + "h" + " + " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W");	
	    			  Pic16F84Registers.W_REGISTER = (byte)(Pic16F84Registers.W_REGISTER & value);
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
		  			  System.out.println("ANDWF: " + String.format("%2X", value) + "h" + " + " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to File Register");	
	    			  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)(Pic16F84Registers.W_REGISTER & value));
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  } 				      	  
	        break;
	        
	        
	        /* >-----------------------------------------------------------------------< */
	        
	        
	      case 0x0180: // CLRF (Set File Register to 0)  
	          System.out.println("CLRF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {  
	    		  // Clearing directly
		          System.out.println("CLRF" + " Clearing: " + String.format("%2X", opcAndArguments[2]) + "h" + " to 0");
	    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), 0);
	    		   
	 			  checkRegisterForZero((byte)Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit()));
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Clearing indirectly
		          System.out.println("CLRF" + " Clearing: " + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()) + "h" + " to 0");
	    		  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), 0);
	    		  
	    		  checkRegisterForZero((byte)Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(),  Pic16F84Registers.getBankBitFromFSR()));
	    	  } 		         
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	      case 0x0100: // CLRF (Set W-Register to 0) 
	          System.out.println("CLRW" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	          System.out.println("CLRW" + " Clearing: " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h" + " to 0");
	    	  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), 0);
	    	  Pic16F84Registers.W_REGISTER = 0;
	 		  checkRegisterForZero(Pic16F84Registers.W_REGISTER);
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0900: // COMF (Set File Register to its complement)  
	          System.out.println("COMF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {  
	    		  // Fetch Value directly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
	    		  
	    		  // Execute operation (complement)
	    		  value = (~value);
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("COMF" + " Complement: " + value + " from " +  String.format("%2X", opcAndArguments[2]) + "h" + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("COMF" + " Complement: " + value + " from " +  String.format("%2X", opcAndArguments[2]) + "h" + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch Value indirectly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  // Execute operation (complement)
	    		  value = (~value);
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("COMF" + " Complement: " + value + " from " +  String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()) + "h" + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("COMF" + " Complement: " + value + " from " +  String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()) + "h" + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  } 	
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0300: // DECF (Decrement File Register)
	          System.out.println("DECF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {  
	    		  // Fetch Value directly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
	    		  
	    		  // Execute operation (decrement)
	    		  value--;
	    		  if(value < 0)
	    			  value = 255;
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("DECF" + " Decremented: " + String.format("%2X", value) + "h" + " from " +  String.format("%2X", opcAndArguments[2]) + "h" + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("DECF" + " Decremented: " + String.format("%2X", value) + "h" + " from " +  String.format("%2X", opcAndArguments[2]) + "h" + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch Value indirectly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  // Execute operation (decrement)
	    		  value--;
	    		  if(value < 0)
	    			  value = 255;
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("DECF" + " Decremented: " + String.format("%2X", value) + "h" + " from " +  String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()) + "h" + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("DECF" + " Decremented: " + String.format("%2X", value) + "h" + " from " +  String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()) + "h" + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  } 
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0B00: //DECFSZ (Decrements 1 from File Register and skip next instruction if file register value falls to 0)
				 System.out.println("DECFSZ" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
		    	 // Direct Addressing
		    	 if(opcAndArguments[2] != 0)
		    	  {  
		    		  System.out.println("DECFSZ: " + "decrement value from " + String.format("%2X", opcAndArguments[2]));	
		    		   
		    		  // Fetch Value directly	    		  
		    		  byte value = (byte)Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
		    		 
		    		  // Execute operation (decrement and skip next instruction if 0) 
		    		  value--;
		    		  
		    		  if(value == 0)
		    		  {
		    		   System.out.println("DECFSZ: " + "Executing NOP and skip next instruction!");	
	    			   System.out.println("====================================================================");  
	    			   CommandExecution.execute(0x0000);
	    			   System.out.println("====================================================================");  
		    		  }
		    		  else
		    		  {
		    		   System.out.println("DECFSZ: " + "Going on normally...");		  
		    		  }			

		    		  // Destination -> W_Register
		    		  if(opcAndArguments[1] == 0)
		    		  {
		    			  System.out.println("DECFSZ" + " storing " + String.format("%2X", value) + "h" + " to W");
		    			  Pic16F84Registers.W_REGISTER = (byte)value;
		    		  }
		    		  // Destination -> File Register
		    		  else if(opcAndArguments[1] == 1)
		    		  {
		    			  System.out.println("DECFSZ" + " storing " + String.format("%2X", value) + "h" + " to File Register" + String.format("%2X", opcAndArguments[2]));
			    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
		    		  }
		    		  // Destination Error
		    		  else
		    			  System.out.println(">>>ERROR: Destination Bit Unclear");
		    		  
		    	  }
		    	  // Indirect Addressing
		    	  else
		    	  {
		    		  System.out.println("DECFSZ: " + "decrement value from " + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()));
		    		  
		    		  // Fetch Value indirectly	    		  
		    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
		    		  
		    		  // Execute operation (decrement and skip next instruction if 0) 
		    		  value--;
		    		  
		    		  if(value == 0)
		    		  {
		    		   System.out.println("DECFSZ: " + "Executing NOP and skip next instruction!");	
	    			   System.out.println("====================================================================");  
	    			   CommandExecution.execute(0x0000);
	    			   System.out.println("====================================================================");  
		    		  }
		    		  else
		    		  {
		    		   System.out.println("DECFSZ: " + "Going on normally...");		  
		    		  }			

		    		  // Destination -> W_Register
		    		  if(opcAndArguments[1] == 0)
		    		  {
		    			  System.out.println("DECFSZ" + " storing " + String.format("%2X", value) + "h" + " to W");
		    			  Pic16F84Registers.W_REGISTER = (byte)value;
		    		  }
		    		  // Destination -> File Register
		    		  else if(opcAndArguments[1] == 1)
		    		  {
		    			  System.out.println("DECFSZ" + " storing " + String.format("%2X", value) + "h" + " to File Register" + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()));
			    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
		    		  }
		    		  // Destination Error
		    		  else
		    			  System.out.println(">>>ERROR: Destination Bit Unclear");
		    	  } 
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0A00: // INCF (Increment File Register)
	          System.out.println("INCF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {  
	    		  // Fetch Value directly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
	    		  
	    		  // Execute operation (Increment)
	    		  value++;
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("INCF" + " Incremented: " + String.format("%2X", value) + "h" + " from " +  String.format("%2X", opcAndArguments[2]) + "h" + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("INCF" + " Incremented: " + String.format("%2X", value) + "h" + " from " +  String.format("%2X", opcAndArguments[2]) + "h" + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch Value indirectly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  // Execute operation (Increment)
	    		  value++;
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("INCF" + " Incremented: " + String.format("%2X", value) + "h" + " from " +  String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()) + "h" + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("INCF" + " Incremented: " + String.format("%2X", value) + "h" + " from " +  String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()) + "h" + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  } 
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0F00: //INCFSZ (increment 1 at File Register and skip next instruction if file register value rises to 0)  
				 System.out.println("INCFSZ" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
		    	 // Direct Addressing
		    	 if(opcAndArguments[2] != 0)
		    	  {  
		    		  System.out.println("INCFSZ: " + "increment value at " + String.format("%2X", opcAndArguments[2]));	
		    		   
		    		  // Fetch Value directly	    		  
		    		  byte value = (byte)Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
		    		 
		    		  // Execute operation (decrement and skip next instruction if 0) 
		    		  value++;
		    		  
		    		  if(value == 0)
		    		  {
		    		   System.out.println("INCFSZ: " + "Executing NOP and skip next instruction!");	
	    			   System.out.println("====================================================================");  
	    			   CommandExecution.execute(0x0000);
	    			   System.out.println("====================================================================");  
		    		  }
		    		  else
		    		  {
		    		   System.out.println("INCFSZ: " + "Going on normally...");		  
		    		  }			

		    		  // Destination -> W_Register
		    		  if(opcAndArguments[1] == 0)
		    		  {
		    			  System.out.println("INCFSZ" + " storing " + String.format("%2X", value) + "h" + " to W");
		    			  Pic16F84Registers.W_REGISTER = (byte)value;
		    		  }
		    		  // Destination -> File Register
		    		  else if(opcAndArguments[1] == 1)
		    		  {
		    			  System.out.println("INCFSZ" + " storing " + String.format("%2X", value) + "h" + " to File Register" + String.format("%2X", opcAndArguments[2]));
			    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
		    		  }
		    		  // Destination Error
		    		  else
		    			  System.out.println(">>>ERROR: Destination Bit Unclear");  
		    	  }
		    	  // Indirect Addressing
		    	  else
		    	  {
		    		  System.out.println("INCFSZ: " + "increment value at " + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()));
		    		  
		    		  // Fetch Value indirectly	    		  
		    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
		    		  
		    		  // Execute operation (decrement and skip next instruction if 0) 
		    		  value++;
		    		  
		    		  if(value == 0)
		    		  {
		    		   System.out.println("INCFSZ: " + "Executing NOP and skip next instruction!");	
	    			   System.out.println("====================================================================");  
	    			   CommandExecution.execute(0x0000);
	    			   System.out.println("====================================================================");  
		    		  }
		    		  else
		    		  {
		    		   System.out.println("INCFSZ: " + "Going on normally...");		  
		    		  }			

		    		  // Destination -> W_Register
		    		  if(opcAndArguments[1] == 0)
		    		  {
		    			  System.out.println("INCFSZ" + " storing " + String.format("%2X", value) + "h" + " to W");
		    			  Pic16F84Registers.W_REGISTER = (byte)value;
		    		  }
		    		  // Destination -> File Register
		    		  else if(opcAndArguments[1] == 1)
		    		  {
		    			  System.out.println("INCFSZ" + " storing " + String.format("%2X", value) + "h" + " to File Register" + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()));
			    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
		    		  }
		    		  // Destination Error
		    		  else
		    			  System.out.println(">>>ERROR: Destination Bit Unclear");
		    	  } 
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0400: // IORWF (W IOR File Register to  or File Register
	          System.out.println("IORF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {  
	    		  // Fetch Value directly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
	    		  
	    		  // Execute operation (IOR)
	    		  value = (byte)((value | Pic16F84Registers.W_REGISTER));
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("IORF " + String.format("%2X", value) + "h" + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("IORF " + String.format("%2X", value) + "h" + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch Value indirectly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  // Execute operation (complement)
	    		  value = (byte)(value | Pic16F84Registers.W_REGISTER);
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("IORF " + String.format("%2X", value) + "h" + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("IORF " + String.format("%2X", value) + "h" + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  } 
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0800: // MOVF (Move Register Value to File Register or W_Register)
				System.out.println("MOVF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {
	    		  // Fetch value directly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
	    		  checkRegisterForZero((byte)value); 

	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	  				  System.out.println("MOVF: " + "Moving " + String.format("%2X", value) + "h" + "to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	  				  System.out.println("MOVF: " + "Moving " + String.format("%2X", value) + "h" + "to File Register");
	    			  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch value indirectly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  checkRegisterForZero((byte)value); 
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("MOVF: " + "Moving " + String.format("%2X", (byte)value) + "h" + "to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("MOVF: " + "Moving " + String.format("%2X", (byte)value) + "h" + "to File Register");
	    			  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  break;
	    	  
	    	  
	    	  /* >-----------------------------------------------------------------------< */
	    	
	    	  
	      case 0x0080: // MOVWF (Move Value of W_Register to file Register)
				System.out.println("MOVWF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
				System.out.println("MOVWF: " + "Moving " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h" + " to " + String.format("%2X", opcAndArguments[2]));
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), Pic16F84Registers.W_REGISTER);
	    	  // Indirect Addressing
	    	  else
	    		  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), Pic16F84Registers.W_REGISTER);
	    	  break;
	    	  
	    	  
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	  
	      case 0x0000: //NOP (doing absolutely nothing... but wasting CPU-Cycles)
	    	System.out.println("NOP" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	System.out.println("NOP: Doing nothing for now...");
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0D00: // RLF (Shift value of file register left once and storing left-falling-off bit in C-Flag)
		    System.out.println("RLF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {
	    		  // Fetch value directly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit()); 

	    		  //Execute Operation (rotate left through carry)
	    		  int carryBit;
	    		  carryBit = (value & 0x0080) >> 7;
	    		  value = (byte)(value & 0x007F);
	    		  value = (byte)(value << 1);
	    		  value += (Pic16F84Registers.PSW & 0b00000001);
	    		  
	    		  if(carryBit == 1)
		    		    Pic16F84Registers.set_Flag("C_FLAG");
	    		  else if(carryBit == 0)
	    			  Pic16F84Registers.reset_Flag("C_FLAG");
	    		    else
	    		    	System.out.println(">>>ERROR: Wrong value for carry-bit!");
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	  				  System.out.println("RLF: " + "Moving " + String.format("%2X", value) + "h" + "to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	  				  System.out.println("RLF: " + "Moving " + String.format("%2X", value) + "h" + "to File Register");
	    			  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch value indirectly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  //Execute Operation (rotate left through carry)
	    		  int carryBit;
	    		  carryBit = (value & 0x0080) >> 7;
	    		  value = (byte)(value & 0x007F);
	    		  value = (byte)(value << 1);
	    		  value += (Pic16F84Registers.PSW & 0b00000001);
	    		  
	    		  if(carryBit == 1)
		    		    Pic16F84Registers.set_Flag("C_FLAG");
	    		  else if(carryBit == 0)
	    			  Pic16F84Registers.reset_Flag("C_FLAG");
	    		    else
	    		    	System.out.println(">>>ERROR: Wrong value for carry-bit!");
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("RLF: " + "Moving " + String.format("%2X", (byte)value) + "h" + "to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("RLF: " + "Moving " + String.format("%2X", (byte)value) + "h" + "to File Register");
	    			  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0C00: //RRF (Shift value of file register right once and storing right-falling-off bit in C-Flag)
			    System.out.println("RRF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
		    	  // Direct Addressing
		    	  if(opcAndArguments[2] != 0)
		    	  {
		    		  // Fetch value directly from File Register
		    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit()); 

		    		  //Execute Operation (rotate left through carry)
		    		  int carryBit;
		    		  carryBit = (value & 0x0001);
		    		  value = (byte)(value >> 1);
		    		  value += ((Pic16F84Registers.PSW & 0b00000001) << 7);
		    		  
		    		  if(carryBit == 1)
			    		    Pic16F84Registers.set_Flag("C_FLAG");
		    		  else if(carryBit == 0)
		    			  Pic16F84Registers.reset_Flag("C_FLAG");
		    		    else
		    		    	System.out.println(">>>ERROR: Wrong value for carry-bit!");
		    		  
		    		  // Destination -> W_Register
		    		  if(opcAndArguments[1] == 0)
		    		  {
		  				  System.out.println("RRF: " + "Moving " + String.format("%2X", value) + "h" + "to W");
		    			  Pic16F84Registers.W_REGISTER = (byte)value;
		    		  }
		    		  // Destination -> File Register
		    		  else if(opcAndArguments[1] == 1)
		    		  {
		  				  System.out.println("RRF: " + "Moving " + String.format("%2X", value) + "h" + "to File Register");
		    			  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
		    		  }
		    		  // Destination Error
		    		  else
		    			  System.out.println(">>>ERROR: Destination Bit Unclear");
		    	  }
		    	  // Indirect Addressing
		    	  else
		    	  {
		    		  // Fetch value indirectly from File Register
		    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
		    		  
		    		  //Execute Operation (rotate left through carry)
		    		  int carryBit;
		    		  carryBit = (value & 0x0001);
		    		  value = (byte)(value >> 1);
		    		  value += ((Pic16F84Registers.PSW & 0b00000001) << 7);
		    		  
		    		  if(carryBit == 1)
			    		    Pic16F84Registers.set_Flag("C_FLAG");
		    		  else if(carryBit == 0)
		    			  Pic16F84Registers.reset_Flag("C_FLAG");
		    		    else
		    		    	System.out.println(">>>ERROR: Wrong value for carry-bit!");
		    		  
		    		  // Destination -> W_Register
		    		  if(opcAndArguments[1] == 0)
		    		  {
		    			  System.out.println("RRF: " + "Moving " + String.format("%2X", (byte)value) + "h" + "to W");
		    			  Pic16F84Registers.W_REGISTER = (byte)value;
		    		  }
		    		  // Destination -> File Register
		    		  else if(opcAndArguments[1] == 1)
		    		  {
		    			  System.out.println("RRF: " + "Moving " + String.format("%2X", (byte)value) + "h" + "to File Register");
		    			  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
		    		  }
		    		  // Destination Error
		    		  else
		    			  System.out.println(">>>ERROR: Destination Bit Unclear");
		    	  }
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0200: // SUBWF (W or File Register(d-Bit) = File Register - W)    
				System.out.println("SUBWF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {
	    		  // Fetch value directly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());

	    		  //Execute Operation (Subtraction)
	  		      checkRegisterForDigitCarry(value, Pic16F84Registers.W_REGISTER, "Subtraction");
	  		      checkForOverflow(value, Pic16F84Registers.W_REGISTER, "Subtraction"); 
	  			
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	  				System.out.println("SUBWF: " + String.format("%2X", value) + "h" + " - " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W");	
	  				checkRegisterForZero((byte)((value - Pic16F84Registers.W_REGISTER)& 0x00FF));
	    			Pic16F84Registers.W_REGISTER = (byte)((value - Pic16F84Registers.W_REGISTER) & 0x00FF);
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	  				System.out.println("SUBWF: " + String.format("%2X", value) + "h" + " - " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to File Register");
	  				checkRegisterForZero((byte)(value - Pic16F84Registers.W_REGISTER));
	    		    Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)((value - Pic16F84Registers.W_REGISTER)& 0x00FF));
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch value indirectly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  //Execute Operation (Subtraction)
	  		      checkRegisterForDigitCarry(value, Pic16F84Registers.W_REGISTER, "Subtraction");
	  		      checkForOverflow(value, Pic16F84Registers.W_REGISTER, "Subtraction"); 
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	  				System.out.println("SUBWF: " + String.format("%2X", value) + "h" + " - " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W");	
	  				checkRegisterForZero((byte)(value - Pic16F84Registers.W_REGISTER));
	    			Pic16F84Registers.W_REGISTER = (byte)((value - Pic16F84Registers.W_REGISTER)& 0x00FF);
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	  			   System.out.println("SUBWF: " + String.format("%2X", value) + "h" + " - " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to File Register");
	  			   checkRegisterForZero((byte)(value - Pic16F84Registers.W_REGISTER));
	    		   Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)((value - Pic16F84Registers.W_REGISTER)& 0x00FF));
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }	    	
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0E00: // SWAPF (Swaps lower 4 Bit with highter 4 Bit)
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {
	    		  // Fetch value directly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());

	    		  //Execute Operation (Swap)
	  		      byte lower4Bit = (byte)((value & 0x0F) << 4);
	  		      byte higher4Bit = (byte)((value & 0xF0) >> 4);
	  		      
	  		      byte swapResult = (byte)(lower4Bit + higher4Bit);
	  			
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	  				System.out.println("SWAPF: " + "swap nibbles of " + String.format("%2X", opcAndArguments[2]) + "h" + " to W");	
	    			Pic16F84Registers.W_REGISTER = (byte)swapResult;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			System.out.println("SWAPF: " + "swap nibbles of " + String.format("%2X", opcAndArguments[2]) + "h" + " to File Register");		
	    		    Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)swapResult);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch value indirectly from File Register
	    		  byte value = (byte) Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  //Execute Operation (Swap)
	  		      byte lower4Bit = (byte)((value & 0x0F) << 4);
	  		      byte higher4Bit = (byte)((value & 0xF0) >> 4);
	  		      
	  		      byte swapResult = (byte)(lower4Bit + higher4Bit);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
		  			  System.out.println("SWAPF: " + "swap nibbles of " + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()) + "h" + " to W");		
	    			  Pic16F84Registers.W_REGISTER = (byte)swapResult;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
		  			  System.out.println("SWAPF: " + "swap nibbles of " + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()) + "h" + " to File Register");	
	    			  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)swapResult);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }	  
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x0600: // XORWF (W XOR File Register to W or File Register)
	          System.out.println("XOR" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	  // Direct Addressing
	    	  if(opcAndArguments[2] != 0)
	    	  {  
	    		  // Fetch Value directly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
	    		  
	    		  // Execute operation (IOR)
	    		  value = (byte)((value ^ Pic16F84Registers.W_REGISTER));
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("XOR " + String.format("%2X", (byte)value) + "h" + " with  " + String.format("%2X", Pic16F84Registers.W_REGISTER) + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("XOR " + String.format("%2X", (byte)value) + "h" + " with  " + String.format("%2X", Pic16F84Registers.W_REGISTER) + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch Value indirectly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  // Execute operation (complement)
	    		  value = (byte)(value ^ Pic16F84Registers.W_REGISTER);
	    		  checkRegisterForZero((byte)value);
	    		  
	    		  // Destination -> W_Register
	    		  if(opcAndArguments[1] == 0)
	    		  {
	    			  System.out.println("XOR " + String.format("%2X", (byte)value) + "h" + " with  " + String.format("%2X", Pic16F84Registers.W_REGISTER) + " to W");
	    			  Pic16F84Registers.W_REGISTER = (byte)value;
	    		  }
	    		  // Destination -> File Register
	    		  else if(opcAndArguments[1] == 1)
	    		  {
	    			  System.out.println("XOR " + String.format("%2X", (byte)value) + "h" + " with  " + String.format("%2X", Pic16F84Registers.W_REGISTER) + " to File Register");
		    		  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
	    		  }
	    		  // Destination Error
	    		  else
	    			  System.out.println(">>>ERROR: Destination Bit Unclear");
	    	  } 
	    	break;
	    	
	    	
            /* >-----------------------------------------------------------------------< */
            /* >----------------Bit-oriented File Register Operations------------------< */
            /* >-----------------------------------------------------------------------< */
	    
	      case 0x1000: //BCF (clears 1 bit selected by opcAndAgument[1] in indirect File Register or directly in opcAndArguments[2])
			 System.out.println("BCF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	 // Direct Addressing
	    	 if(opcAndArguments[2] != 0)
	    	  {  
	    		  // Fetch Value directly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
	    		  
	    		  
	    		  // Execute operation (Bit clearing)
	    		  value = (value & ~(1 << opcAndArguments[1]));

	  			  System.out.println("BCF: " + "Clearing bit " + String.format("%2X", opcAndArguments[1]) + "h" + " from " + String.format("%2X", opcAndArguments[2]));	
		    	  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch Value indirectly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  // Execute operation (Bit clearing)
	    		  value = (value & ~(1 << opcAndArguments[1]));

	  			  System.out.println("BCF: " + "Clearing bit " + String.format("%2X", opcAndArguments[1]) + "h" + " from " + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()));	
		    	  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
	    	  } 
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x1400:  //BSF (set 1 bit selected by opcAndAgument[1] in indirect File Register or directly in opcAndArguments[2])
			 System.out.println("BSF" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	    	 // Direct Addressing
	    	 if(opcAndArguments[2] != 0)
	    	  {  
	    		  // Fetch Value directly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
	    		  
	    		  
	    		  // Execute operation (Bit clearing)
	    		  value = (value | (1 << opcAndArguments[1]));

	  			  System.out.println("BSF: " + "Clearing bit " + String.format("%2X", opcAndArguments[1]) + "h" + " from " + String.format("%2X", opcAndArguments[2]));	
		    	  Pic16F84Registers.writeFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit(), (byte)value);
	    	  }
	    	  // Indirect Addressing
	    	  else
	    	  {
	    		  // Fetch Value indirectly	    		  
	    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
	    		  
	    		  // Execute operation (Bit clearing)
	    		  value = (value | (1 << opcAndArguments[1]));

	  			  System.out.println("BSF: " + "Clearing bit " + String.format("%2X", opcAndArguments[1]) + "h" + " from " + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()));	
		    	  Pic16F84Registers.writeFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR(), (byte)value);
	    	  } 
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x1800: //BTFSC (Skip the next instruction if the bit is set to 0 selected by opcAndArguments[1] from the File Register - Value (direct or indirect), or continue normally if it�s set to 1)
				 System.out.println("BTFSC" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
		    	 // Direct Addressing
		    	 if(opcAndArguments[2] != 0)
		    	  {  
		    		  System.out.println("BTFSC: " + "Test bit " + String.format("%2X", opcAndArguments[1]) + " from " + "file register " + String.format("%2X", opcAndArguments[2]));	
		    		   
		    		  // Fetch Value directly	    		  
		    		  int value = Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());
		    		 
		    		  // Execute operation (check for Bit (1 or 0)) 
		    		  int bitValue = (value & (1 << opcAndArguments[1]));
		    		  if(bitValue == 0)
		    		  {
		    		   System.out.println("BTFSC: " + "Executing NOP and skip next instruction!");	
	    			   System.out.println("====================================================================");  
	    			   CommandExecution.execute(0x0000);
	    			   System.out.println("====================================================================");  
		    		  }
		    		  else if((bitValue >> opcAndArguments[1]) == 1)
		    		  {
		    		   System.out.println("BTFSC: " + "Going on normally...");		  
		    		  }			
		    		  else
		    		  {
		    		   System.out.println(">>>ERROR " + "Invalid value of Result-Bit!");
		    		  }
		    	  }
		    	  // Indirect Addressing
		    	  else
		    	  {
		    		  System.out.println("BTFSC: " + "Test bit " + String.format("%2X", opcAndArguments[1]) + " from " + "file register " + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()));	
		    		  
		    		  // Fetch Value indirectly	    		  
		    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
		    		  
		    		  // Execute operation (check for Bit (1 or 0)) 
		    		  int bitValue = (value & (1 << opcAndArguments[1]));
		    		  if(bitValue == 0)
		    		  {
		    		   System.out.println("BTFSC: " + "Executing NOP and skip next instruction!");	
	    			   System.out.println("====================================================================");  
	    			   CommandExecution.execute(0x0000);
	    			   System.out.println("====================================================================");  
		    		  }
		    		  else if((bitValue >> opcAndArguments[1]) == 1)
		    		  {
		    		   System.out.println("BTFSC: " + "Going on normally...");		  
		    		  }			
		    		  else
		    		  {
		    		   System.out.println(">>>ERROR " + "Invalid value of Result-Bit!");
		    		  }
		    	  } 
	    	break;
	    	
	    	
	    	/* >-----------------------------------------------------------------------< */
	    	
	    	
	      case 0x1C00: //BTFSS (Skip the next instruction if the bit is set to 1 selected by opcAndArguments[1] from the File Register - Value (direct or indirect), or continue normally if it�s set to 0)	  
				 System.out.println("BTFSS" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
		    	 // Direct Addressing
		    	 if(opcAndArguments[2] != 0)
		    	  {  
		    		  System.out.println("BTFSS: " + "Test bit " + String.format("%2X", opcAndArguments[1]) + " from " + "file register " + String.format("%2X", opcAndArguments[2]));	
		    		  
		    		  // Fetch Value directly	    		  
		    		  int value = Pic16F84Registers.readFileRegisterValue(opcAndArguments[2], Pic16F84Registers.getRP0Bit());	  
		    		  
		    		  // Execute operation (check for Bit (1 or 0)) 
		    		  int bitValue = (value & (1 << opcAndArguments[1]));
		    		  if((bitValue >> opcAndArguments[1]) ==1)
		    		  {
		    		   System.out.println("BTFSS: " + "Executing NOP and skip next instruction!");	
	    			   System.out.println("====================================================================");  
	    			   Pic16F84Registers.INSTRUCTION_REGISTER = 0x0000;
	    			   CommandExecution.execute(Pic16F84Registers.INSTRUCTION_REGISTER);
	    			   System.out.println("====================================================================");  
		    		  }
		    		  else if(bitValue == 0)
		    		  {
		    		   System.out.println("BTFSS: " + "Going on normally...");		  
		    		  }			
		    		  else
		    		  {
		    			  System.out.println("Else If: "+ (bitValue >> opcAndArguments[1]));
		    		   System.out.println(">>>ERROR " + "Invalid value of Result-Bit!");
		    		  }
		    	  }
		    	  // Indirect Addressing
		    	  else
		    	  {
		    		  System.out.println("BTFSS: " + "Test bit " + String.format("%2X", opcAndArguments[1]) + " from " + "file register " + String.format("%2X", Pic16F84Registers.getIndirectAdressFromFSR()));	
		    		  
		    		  // Fetch Value indirectly	    		  
		    		  int value = Pic16F84Registers.readFileRegisterValue(Pic16F84Registers.getIndirectAdressFromFSR(), Pic16F84Registers.getBankBitFromFSR());
		    		  
		    		  // Execute operation (check for Bit (1 or 0)) 
		    		  int bitValue = (value & (1 << opcAndArguments[1]));
		    		  if((bitValue >> opcAndArguments[1]) ==1)
		    		  {
		    		   System.out.println("BTFSS: " + "Executing NOP and skip next instruction!");		    		    
	    			   System.out.println("====================================================================");  
	    			   Pic16F84Registers.INSTRUCTION_REGISTER = 0x0000;
	    			   CommandExecution.execute(Pic16F84Registers.INSTRUCTION_REGISTER);
	    			   System.out.println("====================================================================");  
		    		  }
		    		  else if(bitValue == 0)
		    		  {
		    		   System.out.println("BTFSS: " + "Going on normally...");		  
		    		  }			
		    		  else
		    		  {
		    		   System.out.println(">>>ERROR " + "Invalid value of Result-Bit!");
		    		  }
		    	  } 
	        break;
	        
	        
            /* >-----------------------------------------------------------------------< */
            /* >----------------Literal and Control Operations-----------------< */
            /* >-----------------------------------------------------------------------< */
	    	 
	      case 0x3E00: //ADDLW (A1 + W to W)
			System.out.println("ADDLW" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("ADDLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " + " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );	
		    checkRegisterForDigitCarry(opcAndArguments[1], Pic16F84Registers.W_REGISTER, "Addition");		    
			checkForOverflow((byte)opcAndArguments[1], Pic16F84Registers.W_REGISTER, "Addition");
			Pic16F84Registers.W_REGISTER = (byte) (opcAndArguments[1] + Pic16F84Registers.W_REGISTER); 		
			checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			
			
			/* >-----------------------------------------------------------------------< */
			
			
	      case 0x3900: //ANDLW (A1 AND W to W)
			System.out.println("ANDLW" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("ANDLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " AND " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );
			Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER & opcAndArguments[1]); 				  
			checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	 
			
			
			/* >-----------------------------------------------------------------------< */
	    	 
			
	      case 0x2000: //CALL (pushes the PC onto the stack and overwrites it with the value in argument 1
			System.out.println("CALL" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("CALL: " + "Calling function at: " + String.format("%2X", opcAndArguments[1]) + "h");
	    	Pic16F84Registers.push(Pic16F84Registers.PC);
	    	Pic16F84Registers.PC = (short)opcAndArguments[1];
	    	Pic16F84Registers.load2BitPCLATHToPC();
	    	Pic16F84Registers.loadPCToPCL();
	    	Simulator.increaseRuntimeCounter();
			break;
			
			
			/* >-----------------------------------------------------------------------< */
	     
			
	      case 0x0064:
	    	System.out.println(">>>Not implemented: " + "CLRWDT" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			break;
			
			
			/* >-----------------------------------------------------------------------< */
			
			
	      case 0x2800: //GOTO (Jump to adress A1)
			System.out.println("GOTO" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("GOTO: " + "Jumping to adress: " + String.format("%2X", opcAndArguments[1]) + "h");
			Pic16F84Registers.computedGOTO((short)opcAndArguments[1]);
		  	Simulator.increaseRuntimeCounter();
			break;	
			
			
			/* >-----------------------------------------------------------------------< */
			
			
	      case 0x3800: //IORLW (A1 IOR W to W)
			System.out.println("IORLW" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("IORLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " IOR " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );
		    Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER | opcAndArguments[1]); 				  
		    checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			
			
			/* >-----------------------------------------------------------------------< */   	
			
			
	      case 0x3000: //MOVLW (Move A1 to W)
			System.out.println("MOVLW" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("MOVLW: " + "Moving " + String.format("%2X", opcAndArguments[1]) + "h" + " to W" );
		    Pic16F84Registers.W_REGISTER = (byte) opcAndArguments[1];
		    checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			
			
			/* >-----------------------------------------------------------------------< */
	     
			
	      case 0x0009:
	        System.out.println(">>>Not implemented: " + "RETFIE" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
	      	Simulator.increaseRuntimeCounter();
			break;	
			
			
			/* >-----------------------------------------------------------------------< */
			   	
			
	      case 0x3400: //RETLW (returns from a function and pops the saved PC from the stack and additionally writes opcAndArguments[1] to W)
			System.out.println("RETLW" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("RETLW: " + "Jumping back to: " + String.format("%2X", Pic16F84Registers.STACK[Pic16F84Registers.STACKPOINTER]) + "h" + "and moving " + String.format("%2X", opcAndArguments[1]) + "to W");
	    	Pic16F84Registers.PC = Pic16F84Registers.pop();
	    	Pic16F84Registers.loadPCToPCL();
	    	Pic16F84Registers.W_REGISTER = (byte)opcAndArguments[1];
	      	Simulator.increaseRuntimeCounter();
			break;	
			
			
			/* >-----------------------------------------------------------------------< */
			   		
			
	      case 0x0008: //RETURN (returns from a function and pops the saved PC from the stack)
			System.out.println("RETURN" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("RETURN: " + "Jumping back to: " + String.format("%2X", Pic16F84Registers.STACK[Pic16F84Registers.STACKPOINTER]) + "h");
	    	Pic16F84Registers.PC = Pic16F84Registers.pop();
	    	Pic16F84Registers.loadPCToPCL();
	      	Simulator.increaseRuntimeCounter();
			break;	
			
			
			/* >-----------------------------------------------------------------------< */
			   	
			
	      case 0x0063:
			System.out.println(">>>Not implemented: " + "SLEEP" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			break;	
			
			
			/* >-----------------------------------------------------------------------< */
			   	
			
	      case 0x3C00: //SUBLW (A1 - W to W)
			System.out.println("SUBLW" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("SUBLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " - " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );
			checkRegisterForDigitCarry(opcAndArguments[1],Pic16F84Registers.W_REGISTER, "Subtraction");
			checkForOverflow((byte)opcAndArguments[1], Pic16F84Registers.W_REGISTER, "Subtraction");	
		    Pic16F84Registers.W_REGISTER = (byte)((opcAndArguments[1] - Pic16F84Registers.W_REGISTER)& 0x00FF); 	
			checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			
			
			/* >-----------------------------------------------------------------------< */
				 
			
		  case 0x3A00: //XORLW (A1 XOR W to W)
			System.out.println("XORLW" + " Arguments: " + String.format("%2X", opcAndArguments[1]) + "h" + "," + String.format("%2X", opcAndArguments[2]) + "h");
			System.out.println("XORLW: " + String.format("%2X", opcAndArguments[1]) + "h" + " XOR " + String.format("%2X", Pic16F84Registers.W_REGISTER) + "h"  + " to W" );
		    Pic16F84Registers.W_REGISTER = (byte) (Pic16F84Registers.W_REGISTER ^ opcAndArguments[1]); 				  
		    checkRegisterForZero(Pic16F84Registers.W_REGISTER);
			break;	
			
			
            /* >-----------------------------------------------------------------------< */
            /* >-------------------------Undefined Operation---------------------------< */
            /* >-----------------------------------------------------------------------< */
			  
			
		  case -1:
		    System.out.println(">>>Not implemented: Undefined instruction found!");
			break;
			
			
			/* >-----------------------------------------------------------------------< */
		 } 	
	  }
    
    
    
    /* >>> FLAG-CHECKER <<< */
	
    //Checks a certain register if it hold the value 0
	static void checkRegisterForZero(byte register)
	  {
	    if(register == 0)
		  Pic16F84Registers.set_Flag("Z_FLAG");
		else
		  Pic16F84Registers.reset_Flag("Z_FLAG");
	  }
	
	//Checks if a Digit Carry is occuring during an arithmetik(+,-,*,:) operation
	static void checkRegisterForDigitCarry(int value1, int value2, String operationType)
	  {	
		switch(operationType)
		{
		case "Addition":
			if((value1 & 0x000F) + (value2 & 0x00F)  > 0xF)
		          Pic16F84Registers.set_Flag("DC_FLAG");
		        else
		          Pic16F84Registers.reset_Flag("DC_FLAG");   
			break;		
		case "Subtraction":
			if((value1 & 0x000F) + ((~value2+1) & 0x000F)  > 0xF)
		          Pic16F84Registers.set_Flag("DC_FLAG");
		        else
		          Pic16F84Registers.reset_Flag("DC_FLAG");   
			break;
		default:
			System.out.println(">>>ERROR: Invalid operation Type!");
			break;
		}
	  }
	
	//Checks for overflows when adding or subtracting
	static void checkForOverflow(int value1, int value2, String operationType)
	  {
		switch(operationType)
		{
		case "Addition":
			int resultAdditon = value1 + value2;
			if(resultAdditon > 255)
		       Pic16F84Registers.set_Flag("C_FLAG");
		    else
		       Pic16F84Registers.reset_Flag("C_FLAG");
			break;		
		case "Subtraction":
			int resultSubtraction = (value1 & 0x00FF) + ((~value2+1) & 0x00FF);
			if(resultSubtraction > 255 )
		       Pic16F84Registers.set_Flag("C_FLAG");
		    else
		       Pic16F84Registers.reset_Flag("C_FLAG");
			break;
		default:
			System.out.println(">>>ERROR: Invalid operation Type!");
			break;
		}	

	  }
	
}
