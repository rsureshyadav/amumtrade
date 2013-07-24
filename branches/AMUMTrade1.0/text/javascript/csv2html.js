//javascript

// The names of our input and output files
var src  = "config/output/amex_output_08_10_2012_02_01.csv";
var dest = "results.html";

// Create some variables
var fso, fin, fout;
var data = new Array();

// Define constants for file access
var forReading   = 1;
var forWriting   = 2;
var forAppending = 8; // not used - given for reference

// Create File System Object and open input and output files
fso  = new ActiveXObject( "Scripting.FileSystemObject" );
fin  = fso.OpenTextFile( src, forReading );
fout = fso.OpenTextFile( dest, forWriting, true );// create file if not found

// Write out header and start of table
fout.WriteLine( htmlHeader() );
fout.WriteLine( "<table border=&#39;0&#39; cellpadding=&#39;1&#39; cellspacing=&#39;0&#39; width=&#39;100%&#39;>" );

// Loop through entire file
while( !fin.AtEndOfStream )
{
 try 
 {
  // Read the next line
  var line = fin.ReadLine();
  
  // If line if blank - skip it
  if( line == "" )
    continue;
  
  // Fill our array &#39;data&#39; which csv data split at &#39;,&#39;
  // If you are using a different seperator, such as a TAB
  // you will need to modify the next item
  // Some examples 
  // data = line.split( "\t" ); // for tab
  // data = line.split( ":" );  // for colon
  // data = line.split( " " );  // for space
  data = line.split( "," );
  
  // Start our table ROW
  fout.WriteLine( "<tr>" );
 
  // Loop through data elements found on current line
  for( i = 0; i < data.length; i++ )
  { 
   // write TD tags to wrap data
   fout.WriteLine( "<td>" + data[i] + "</td>" );
  }
  
  // Close the ROW
  fout.WriteLine( "</tr>\r\n" );
 }
 catch( e )
 {
  WScript.Echo( "Error: " + e.description );
 }
}

// Close TABLE
fout.WriteLine( "</table>" );

// Close HTML page
fout.WriteLine( htmlFooter() );

// Close input and output files
fin.Close();
fout.Close();

 

/*******************************
 HTML Header data
********************************/

function htmlHeader()
{
 var title = "CSV2HTML";
 var head = "<html>\r\n<head>\r\n";
 
 // Title
 head += "<title>" + title + "</title>\r\n";
 
 // Style Sheet
 head += "<style>\r\n";
 head += " TD { \r\n";
 head += "    font-family: verdana;\r\n";
 head += "    font-size: 10pt; \r\n";
 head += "    border-bottom: thin groove lightyellow;\r\n";
 head += "    border-top: thin groove lightyellow;\r\n";
 head += "    color: blue; background: lightgrey;\r\n";
 head += " }\r\n";
 head += "</style>\r\n";
 
 head += "</head>\r\n<body>\r\n";
 return( head );
}

/********************************
 HTML Footer data
*********************************/
function htmlFooter()
{
 var foot = "\r\n</body>\r\n</html>";
 return( foot );
}