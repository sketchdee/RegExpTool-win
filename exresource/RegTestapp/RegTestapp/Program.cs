using System;
using System.Text.RegularExpressions;
using System.IO;
using System.Diagnostics;

namespace donetcoreConsoleApp3
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                if (args.Length < 2)
                    throw new Exception("Need Arguement:" + " - \"regular expression\" - \"object file path\"");
                if (args.Length < 1)
                    if (File.Exists(args[0].Replace("-", "")))
                        throw new Exception("lost regular expression.");
                    else
                        throw new Exception("lost expresssion");

                //if erro not  happened ,read the args.
                string pattern = args[0].Replace("-", "");
                string path = args[1].Replace("-", "");
                string source = "";
                string line;

                System.IO.StreamReader file =
                new System.IO.StreamReader(path,true);
                while ((line = file.ReadLine()) != null)
                {
                    source += line;
                }
                MatchCollection matches = Regex.Matches(source, pattern);
                foreach (Match match in matches)
                    Console.WriteLine(match.Value);
                Process.GetCurrentProcess().Kill();
            }
            catch (IOException IOE)
            {
                Console.WriteLine(IOE.Message);
            }
            catch (Exception e1)
            {
                Console.WriteLine(e1.Message);
            }
            finally
            {
                Process.GetCurrentProcess().Kill();
            }
        }
    }
}
