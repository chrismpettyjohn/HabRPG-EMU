package com.eu.habbo.core;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.util.callback.HTTPPostError;
import gnu.trove.set.hash.THashSet;
import io.netty.util.internal.ConcurrentSet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Logging
{

    private static PrintWriter packetsWriter;
    private static PrintWriter packetsUndefinedWriter;
    private static PrintWriter errorsPacketsWriter;
    private static PrintWriter errorsSQLWriter;
    private static PrintWriter errorsRuntimeWriter;
    private static PrintWriter debugFileWriter;


    public static final String ANSI_BRIGHT = "\u001B[1m";


    public static final String ANSI_ITALICS = "\u001B[3m";


    public static final String ANSI_UNDERLINE = "\u001B[4m";


    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    private final THashSet<Loggable> errorLogs = new THashSet<>(100);


    private final THashSet<Loggable> commandLogs = new THashSet<>(100);

    private ConcurrentSet<Loggable> chatLogs = new ConcurrentSet<>();

    public Logging()
    {

        File packets = new File("logging//packets//defined.txt");

        File packetsUndefined = new File("logging//packets//packets.txt");

        File errorsPackets = new File("logging//errors//packets.txt");

        File errorsSQL = new File("logging//errors//sql.txt");

        File errorsRuntime = new File("logging//errors//runtime.txt");

        File debugFile = new File("logging//debug.txt");

        try
        {
            if (!packets.exists())
            {
                if (!packets.getParentFile().exists())
                {
                    packets.getParentFile().mkdirs();
                }

                packets.createNewFile();
            }

            if (!packetsUndefined.exists())
            {
                if (!packetsUndefined.getParentFile().exists())
                {
                    packetsUndefined.getParentFile().mkdirs();
                }

                packetsUndefined.createNewFile();
            }

            if (!errorsPackets.exists())
            {
                if (!errorsPackets.getParentFile().exists())
                {
                    errorsPackets.getParentFile().mkdirs();
                }

                errorsPackets.createNewFile();
            }

            if (!errorsSQL.exists())
            {
                if (!errorsSQL.getParentFile().exists())
                {
                    errorsSQL.getParentFile().mkdirs();
                }

                errorsSQL.createNewFile();
            }

            if (!errorsRuntime.exists())
            {
                if (!errorsRuntime.getParentFile().exists())
                {
                    errorsRuntime.getParentFile().mkdirs();
                }

                errorsRuntime.createNewFile();
            }

            if (!debugFile.exists())
            {
                if (!debugFile.getParentFile().exists())
                {
                    debugFile.getParentFile().mkdirs();
                }

                debugFile.createNewFile();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            packetsWriter          = new PrintWriter(new FileWriter(packets, true));
            packetsUndefinedWriter = new PrintWriter(new FileWriter(packetsUndefined, true));
            errorsPacketsWriter    = new PrintWriter(new FileWriter(errorsPackets, true));
            errorsSQLWriter        = new PrintWriter(new FileWriter(errorsSQL, true));
            errorsRuntimeWriter    = new PrintWriter(new FileWriter(errorsRuntime, true));
            debugFileWriter        = new PrintWriter(new FileWriter(debugFile, true));
        }
        catch (IOException e)
        {
			System.out.println("[CRITICAL] FAILED TO LOAD LOGGING COMPONENT!");
        }
    }


    public void logStart(Object line)
    {
        System.out.println("[" + Logging.ANSI_BRIGHT + Logging.ANSI_GREEN + "LOADING" + Logging.ANSI_RESET + "] " + line.toString());
    }


    public void logShutdownLine(Object line)
    {
        if(Emulator.getConfig().getBoolean("logging.debug"))
        {
            this.write(debugFileWriter, line.toString());
        }
        System.out.println("[" + Logging.ANSI_BRIGHT + Logging.ANSI_GREEN + "SHUTDOWN" + Logging.ANSI_RESET + "] " + line.toString());
    }

    public void logUserLine(Object line)
    {
        if(Emulator.getConfig().getBoolean("logging.debug"))
        {
            this.write(debugFileWriter, line.toString());
        }

        if (Emulator.getConfig().getBoolean("debug.show.users"))
        {
            System.out.println("[USER] " + line.toString());
        }
    }
    
    public synchronized void logDebugLine(Object line)
    {
        if (line instanceof Throwable)
        {
            this.logErrorLine(line);
            return;
        }
        if (Emulator.getConfig().getBoolean("debug.mode")) {
            System.out.println("[DEBUG] " + line.toString());
        }

        if(Emulator.getConfig().getBoolean("logging.debug"))
        {
            this.write(debugFileWriter, line.toString());
        }
    }
    
    public synchronized void logPacketLine(Object line)
    {
        if (Emulator.getConfig().getBoolean("debug.show.packets")) {
            System.out.println("[" + Logging.ANSI_BLUE + "PACKET" + Logging.ANSI_RESET + "]" + line.toString());
        }

        if(Emulator.getConfig().getBoolean("logging.packets"))
        {
            this.write(packetsWriter, line.toString());
        }
    }
    
    public synchronized void logUndefinedPacketLine(Object line)
    {
        if (Emulator.getConfig().getBoolean("debug.show.packets.undefined"))
        {
            System.out.println("[PACKET] [UNDEFINED] " + line.toString());
        }

        if (Emulator.getConfig().getBoolean("logging.packets.undefined"))
        {
            this.write(packetsUndefinedWriter, line.toString());
        }
    }
    
    public synchronized void logErrorLine(Object line)
    {
        if (Emulator.isReady && Emulator.getConfig().getBoolean("debug.show.errors"))
        {
            System.err.println("[ERROR] " + line.toString());
        }

        if (Emulator.getConfig().loaded && Emulator.getConfig().getBoolean("logging.errors.runtime"))
        {
            this.write(errorsRuntimeWriter, line);
        }

        if(line instanceof Throwable)
        {
            ((Throwable) line).printStackTrace();
            if (line instanceof SQLException)
            {
                this.logSQLException((SQLException) line);
                return;
            }
            Emulator.getThreading().run(new HTTPPostError((Throwable) line));

            this.errorLogs.add(new ErrorLog("Exception", (Throwable) line));

            return;
        }

        this.errorLogs.add(new ErrorLog("Emulator", line.toString()));
    }

    public void logSQLException(SQLException e)
    {
        if(Emulator.getConfig().getBoolean("logging.errors.sql"))
        {
            e.printStackTrace();
            this.write(errorsSQLWriter, e);

            Emulator.getThreading().run(new HTTPPostError(e));
        }
    }

    public void logPacketError(Object e)
    {
        if(Emulator.getConfig().getBoolean("logging.errors.packets"))
        {
            if(e instanceof Throwable)
                ((Exception) e).printStackTrace();

            this.write(errorsPacketsWriter, e);
        }

        if(e instanceof Throwable)
        {
            ((Throwable) e).printStackTrace();
            if (e instanceof SQLException)
            {
                this.logSQLException((SQLException) e);
                return;
            }

            Emulator.getThreading().run(new HTTPPostError((Throwable) e));
        }
    }
    
    public void handleException(Exception e)
    {
        e.printStackTrace();
    }

    private synchronized void write(PrintWriter printWriter, Object message)
    {
        if(printWriter != null && message != null)
        {
            if(message instanceof Throwable)
            {
                ((Exception) message).printStackTrace(printWriter);
            }
            else
            {
                printWriter.write("MSG: " + message.toString() + "\r\n");
            }

            printWriter.flush();
        }
    }

    public void addLog(Loggable log)
    {
        if (log instanceof ErrorLog)
        {
            synchronized (this.errorLogs)
            {
                this.errorLogs.add(log);
            }
        }
        else if (log instanceof CommandLog)
        {
            synchronized (this.commandLogs)
            {
                this.commandLogs.add(log);
            }
        }
    }

    public void addChatLog(Loggable chatLog)
    {
        this.chatLogs.add(chatLog);
    }

    public void saveLogs()
    {
        if (Emulator.getDatabase() != null && Emulator.getDatabase().getDataSource() != null)
        {
            if (!this.errorLogs.isEmpty() || !this.commandLogs.isEmpty() || !this.chatLogs.isEmpty())
            {
                try (Connection connection = Emulator.getDatabase().getDataSource().getConnection())
                {
                    if (!this.errorLogs.isEmpty())
                    {
                        synchronized (this.errorLogs)
                        {
                            try (PreparedStatement statement = connection.prepareStatement(ErrorLog.insertQuery))
                            {
                                for (Loggable log : this.errorLogs)
                                {
                                    log.log(statement);
                                }
                                statement.executeBatch();
                            }
                            this.errorLogs.clear();
                        }
                    }

                    if (!this.commandLogs.isEmpty())
                    {
                        synchronized (this.commandLogs)
                        {
                            try (PreparedStatement statement = connection.prepareStatement(CommandLog.insertQuery))
                            {
                                for (Loggable log : this.commandLogs)
                                {
                                    log.log(statement);
                                }

                                statement.executeBatch();
                            }
                            this.commandLogs.clear();
                        }
                    }

                    if (!this.chatLogs.isEmpty())
                    {
                        ConcurrentSet<Loggable> chatLogs = this.chatLogs;
                        this.chatLogs = new ConcurrentSet<>();

                        try (PreparedStatement statement = connection.prepareStatement(RoomChatMessage.insertQuery))
                        {
                            for (Loggable log : chatLogs)
                            {
                                log.log(statement);
                            }

                            statement.executeBatch();
                        }
                        chatLogs.clear();
                    }
                }
                catch (SQLException e)
                {
                    Emulator.getLogging().logSQLException(e);
                }
            }
        }
    }

    public static PrintWriter getPacketsWriter()
    {
        return packetsWriter;
    }

    public static PrintWriter getPacketsUndefinedWriter()
    {
        return packetsUndefinedWriter;
    }

    public static PrintWriter getErrorsPacketsWriter()
    {
        return errorsPacketsWriter;
    }

    public static PrintWriter getErrorsSQLWriter()
    {
        return errorsSQLWriter;
    }

    public static PrintWriter getErrorsRuntimeWriter()
    {
        return errorsRuntimeWriter;
    }

    public static PrintWriter getDebugFileWriter()
    {
        return debugFileWriter;
    }
}