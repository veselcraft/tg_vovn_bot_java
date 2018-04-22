/*
 * 
 * @vovn_bot by veselcraft
 * created march 2017
 * 0.1
 * 
 */

package tgbot_vovan;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


public class StupidVovnBot extends TelegramLongPollingBot{
	
	// стринги бшлять какието
	public static String statusVova = new String("");
	private static JDesktopPane desktopPane = new JDesktopPane();
	private static JTextArea logs = new JTextArea(10, 38);
	@SuppressWarnings("deprecation")
	private void sendMsgFromGUI() { // тута функция отправки сообщения из ГУИ интерфейса
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(text_chatid.getText());
		sendMessage.setText(text_message.getText());
		try {
			sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	private void logsSetText(String textMessage) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd в HH:mm:ss"); 
		String t1 = sdf.format(cal.getTime());
		String text = logs.getText();
		logs.setText(text + "\n[" + t1 + "] " + textMessage);
	}
	
	public static boolean empty( final String s ) {
		  // Null-safe, short-circuit evaluation.
		  return s == null || s.trim().isEmpty();
		}
	
	@Override
	public String getBotUsername() {
		return name_bot; // тут хуячим имя бота
	}
 
	@Override
	public String getBotToken() {
		return token; // здеся токен
	}
	
	public JButton button_0;
	public JButton button_1;
	public static JTextField textFieldVova;
	public static JInternalFrame frame1;
	public static String token;
	public static String name_bot;
	public static void main(String[] args) { 
		File configFile = new File("config.properties");
		
		try {

			FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		    
			if (empty(props.getProperty("name_bot")) && empty(props.getProperty("token"))) {
				// set the properties value
				props.setProperty("name_bot", "Put name of bot here");
				props.setProperty("token", "Put token of bot here");

				JOptionPane.showMessageDialog(null, "Обнови конфигурационный файл!\nПрограмма закрывается.");
				FileWriter writer = new FileWriter(configFile);
			    props.store(writer, "settings");
				writer.close();	
				System.exit(0);
			}else if (!empty(props.getProperty("name_bot")) && !empty(props.getProperty("token"))){
				token = props.getProperty("token");
				name_bot = props.getProperty("name_bot");
			}


		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			

		}
		
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new StupidVovnBot()); 
			Calendar cal = Calendar.getInstance(); // кал??
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd в HH:mm:ss"); 
			String t1 = sdf.format(cal.getTime()); 
			logs.setTabSize(20);
			logs.setFont(new Font("Monospaced", Font.PLAIN, 13)); // выставляю монохромный шрифт
			logs.setText("["+t1+"] Включен");                     // вывожу сообщение о ВКЛЮЧЕНИИ БОТА!!!!!
		} catch (TelegramApiException e) { // если нихуя не получается то выдаем иссключение и пишем это в консоль
			JOptionPane.showMessageDialog(null, "Обнови конфигурационный файл!\nПрограмма закрывается.");
			e.printStackTrace();
		}
		
		JFrame okno = new JFrame(); // тут же мы собсна создаём ОКОШКО
									// я так знатно переебался с ним в 2017 году, но вполне нормально получилось
									// если знаете жфрейм то в коде разберётесь в полне
		JScrollPane scrollPane = new JScrollPane(logs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		okno.setTitle("Бот Вована @vovn_bot - панель управления");
		okno.setBounds(40, 50, 460, 400);
		
		scrollPane.setViewportBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setWheelScrollingEnabled(true);
        
        JInternalFrame frame1 = new JInternalFrame("Логи бота", true, true, true,
                true);
        frame1.setBounds(0, 0, 230, 120);
        frame1.getContentPane().add(scrollPane);
        frame1.setDefaultCloseOperation( JInternalFrame.DO_NOTHING_ON_CLOSE );
        frame1.pack();
        frame1.setVisible(true);
        
        okno.getContentPane().setLayout(new BorderLayout(0, 0));
        desktopPane.setLayout(null);
        desktopPane.add(frame1);
        JInternalFrame frame2 = new JInternalFrame("Отправить сообщение", true, true, true, true);
        frame2.setBounds(30,30,225,124);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation( JInternalFrame.DO_NOTHING_ON_CLOSE );
        frame2.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
        
        JLabel lblChatId = new JLabel("Chat ID:");
        frame2.getContentPane().add(lblChatId);
        
        text_chatid = new JTextField();
        frame2.getContentPane().add(text_chatid);
        text_chatid.setColumns(10);
        
        JLabel label = new JLabel("Сообщение:");
        frame2.getContentPane().add(label);
        
        text_message = new JTextField();
        frame2.getContentPane().add(text_message);
        text_message.setColumns(10);
        
        JButton button_0 = new JButton("Отправить");
        frame2.add(button_0);
        ListenerButton bl = new StupidVovnBot().new ListenerButton();
        button_0.addActionListener(bl);
        
        desktopPane.add(frame2);
        
        JInternalFrame frame3 = new JInternalFrame("А Вова...", true, true, true, true); // мог бы я ещё сделать веб интерфейс который записывает что делает вова и записывать в текстовой фалй
        																					// а софтинка бы могла поймать этот файлик и туда записать то, что я дрочу. но чёт пиздец лень
        frame3.setBounds(160,160,282,102);
        frame3.setDefaultCloseOperation( JInternalFrame.DO_NOTHING_ON_CLOSE );
        frame3.setVisible(true);
        
        frame3.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
        
        JTextField textFieldVova = new JTextField();
        frame3.getContentPane().add(textFieldVova);
        textFieldVova.setColumns(10);
        
        JButton button_1 = new JButton("Пусть узнают, что ты делал");
        frame3.getContentPane().add(button_1);
        
        button_1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		statusVova = new String(textFieldVova.getText());
        	}
        	
        });
        
        desktopPane.add(frame3);
        okno.getContentPane().add(desktopPane);
        
        // okno.getContentPane().add(scrollPane);
		// okno.getContentPane().add(frame1);
		JMenuBar menubar = new JMenuBar();
		okno.setJMenuBar(menubar);
		JMenu file = new JMenu("Главная");
		menubar.add(file);
		file.add(Выйти);
		JMenu help = new JMenu("Помощь");
		menubar.add(help);
		help.add(Информация_о_суициднике);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);
		try {
			//com.sun.java.swing.plaf.windows.WindowsLookAndFeel
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		      SwingUtilities.updateComponentTreeUI(okno);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }

	}
	
	
	
	private class ListenerButton implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    				sendMsgFromGUI();
	} 
	
	}
	
	static Action Выйти = new AbstractAction("Выход"){
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "see you next time");
			System.exit(0);
			
		}
	};
	
	
	static Action Информация_о_суициднике = new AbstractAction("О программе"){
		@Override
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "Бот вована бай Veselcraft\nсделан в марте 2к17 и наконецто его исходники были выложены год спустя\n@vovn_bot");
		}
	};
	private static JTextField text_chatid;
	private static JTextField text_message;
	private static JTextField textField;
	
	
	
	
	
 
	@Override
	public void onUpdateReceived(Update update) {
		String text = logs.getText();
		Message message = update.getMessage();
		String vzhuh_command_check = null;
		if (message != null && message.hasText()) { // тут все команды 
			if (message.getText().equals("/start") || message.getText().equals("/start@"+getBotUsername())){
				logsSetText("У меня новый пользователь! Yay! Chat ID: "+message.getChatId().toString());
				sendMsg(message, "Привет мой дорогой человек!\n Для помощи наберите /help\n А ещё я няша :3", 1, 1, 1);
			}else if (message.getText().equals("/help") || message.getText().equals("/help@"+getBotUsername()) ){
				logsSetText("У меня просят помощи. Chat ID: "+message.getChatId().toString());
				sendMsg(message, "Помощь значит? Ладно: \n\n*Полезные команды:*\n/help - Помощь \n/search <аргементы> - ищу за тебя в гугле и яндексе\n/write <сообщение> - анонимное сообщение `@veselcraft`'у\n\n*Развлечения:*\n/vzhuh - вжухай в своих чатах совершенно бесплатно (но это не точно)\n/shrug - пожать плечами\n/status - узнать, что `@veselcraft` сейчас делает\n\n*Песенки:*\n/deadline - песенка про дедлайн\n\nА ещё я няша :3", 1, 1, 1);
			}else if (message.getText().startsWith("/vzhuh@"+getBotUsername(), 0) == true || message.getText().startsWith("/vzhuh", 0) == true){
				if (message.getText().startsWith("/vzhuh@"+getBotUsername(), 0) == true) {
					vzhuh_command_check = new String("/vzhuh@"+getBotUsername());
				}else if(message.getText().startsWith("/vzhuh", 0) == true) {
					vzhuh_command_check = new String("/vzhuh"); 
				}
				logsSetText("Кому-то вжухнул! Chat ID: "+message.getChatId().toString());
				sendMsg(message, "``` ∧＿∧\n( ･ω･｡)つ━☆・*。\n⊂  ノ    ・゜+.\nしーＪ   °。+ *´¨)\n         .· ´¸.·*´¨) ¸.·*¨)\n          (¸.·´ (¸.·'* ☆```\n"+message.getText().replace(vzhuh_command_check, ""), 0, 1, 1);
			}else if (message.getText().equals("отсоси") || message.getText().equals("Отсоси")){
				logsSetText("Меня просят скинуть хентай... Ну ок)");
				sendMsg(message, "[есть r34, если что 🌚](https://www.google.ru/search?q=tails+r34)", 1, 1, 1);
			}else if (message.getText().equals("/shrug") || message.getText().equals("/shrug@vovn_bot")){
				logsSetText("Пожал плечами Chat ID: "+message.getChatId().toString());
				sendMsg(message, "¯\\_(ツ)_/¯ ", 0, 0, 1);
			}else if (message.getText().equals("Да")){
				logsSetText("Стандартный прикол №1 Chat ID: "+message.getChatId().toString());
				System.out.println("прикол от МІСТЕРА ВЕСЕЛКРАФТА");
				sendMsg(message, "Поезда", 1, 1, 1);
			}else if (message.getText().startsWith("/search@"+getBotUsername()) || message.getText().startsWith("/search ", 0) == true) {
				String google1 = null;
				if (message.getText().startsWith("/search@"+getBotUsername(), 0) == true) {
					google1 = message.getText().replace("/search@"+getBotUsername(), "");
				}else if(message.getText().startsWith("/search", 0) == true) {
					google1 = message.getText().replace("/search ", "");
				}
				logsSetText("Кто-то пытался загуглить "+google1+" Chat ID: "+message.getChatId().toString());
				String google2 = google1.replace(" ", "+");
				sendMsg(message, "[Результаты в Google](google.com/search?q="+google2+")\n[или в священном и православном Yandex 🌚](yandex.ru/search/?text="+google2+")", 1, 1, 1);
			}else if (message.getText().startsWith("/write")){
				String write1 = message.getText().replace("/write ", "");
				logsSetText("Мистеру веселкрафту отправили это: "+write1+" Chat ID: "+message.getChatId().toString());
				sendMsg(message, "Сообщение отправлено.", 1, 1, 1);
				sendMsg(message, "Сообщение от анонимного чувака: "+write1, 0, 1, 0);
			}else if (message.getText().startsWith("/deadline")){
				sendMsg(message, "между нами Tide и лёд\nи пусть баги никто не найдёт\nдедлайн ближе с каждым днём\nно мы даже не знаем о нём.", 1, 1, 1);
				logsSetText("Тайд и лёд.  Chat ID: "+message.getChatId().toString());
		}else if (message.getText().equals("Джимбо, скажи им!")){
			logsSetText("Сказал им Chat ID: "+message.getChatId().toString());
			sendMsg(message, "Гав, гав, гав! ", 0, 0, 1);
		}else if (message.getText().equals("/status") || message.getText().equals("/status@vovn_bot")){
			System.out.println("Слил твою инфу))))0");
			logsSetText("Слил твою инфу)))0  .  Chat ID: "+message.getChatId().toString());
			if (statusVova.equals("")){
				statusVova = new String("не задал что он сейчас делает. Я без понятия что он делает сейчас. Чесно. ");
			}
			sendMsg(message, "А Вова "+statusVova, 0, 0, 1);
		}
			
			
			
		}
			}
		
	

	private void sendMsg(Message message, String text, Integer replyto, Integer markdown, Integer kb) {
		SendMessage sendMessage1 = new SendMessage();
		if (markdown == 1){
		sendMessage1.enableMarkdown(true);
		}
		if (replyto == 1){
			sendMessage1.setReplyToMessageId(message.getMessageId());
		}
		if (kb == 1) {
		sendMessage1.setChatId(message.getChatId().toString());
		sendMessage1.setText(text);
		try {
			sendMessage(sendMessage1);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		}else{
		sendMessage1.setChatId("137703694");
		sendMessage1.setText(text);
		try {
			sendMessage(sendMessage1);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		}
		
		
	}
	
	



}

