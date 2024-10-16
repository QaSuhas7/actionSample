package org.aia.utility;

import org.testng.*;
import org.testng.util.RetryAnalyzerCount;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CustomTestNGReport implements IReporter {

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		StringBuilder htmlContent = new StringBuilder();
		XmlSuite xmlSuite = xmlSuites.get(0);
		String executedModule = xmlSuite.getName();// Retrieve the XML suit
		long startTime = Long.MAX_VALUE;
		long endTime = Long.MIN_VALUE;

		int passCount = 0;
		int failCount = 0;
		int skipCount = 0;

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult suiteResult : suiteResults.values()) {
				ITestContext testContext = suiteResult.getTestContext();
				long contextStartTime = testContext.getStartDate().getTime();
				long contextEndTime = testContext.getEndDate().getTime();
				startTime = Math.min(startTime, contextStartTime);
				endTime = Math.max(endTime, contextEndTime);
				int passTc=testContext.getPassedTests().size();
				int allTc=testContext.getAllTestMethods().length;
				System.out.println("Passed------"+passTc);
				System.out.println("All------"+allTc);
				if(testContext.getSkippedTests().size()!=0) {
		        if (testContext.getSkippedTests().getAllResults().iterator().next().wasRetried()) {
					skipCount = 0;
					failCount += testContext.getFailedTests().size();
					passCount += testContext.getPassedTests().size();
				}
				}
				else{
					failCount += testContext.getFailedTests().size();
					passCount += testContext.getPassedTests().size();
					skipCount += testContext.getSkippedTests().size();
				}
			}
		}

		// Calculate total number of tests
		int totalTests = passCount + failCount + skipCount;

		// Calculate pass percentage
		float passPercentageNumner = ((float) passCount / totalTests) * 100;
		String passPercentage = String.format("%.2f", passPercentageNumner);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult suiteResult : suiteResults.values()) {
				ITestContext testContext = suiteResult.getTestContext();
				long contextStartTime = testContext.getStartDate().getTime();
				long contextEndTime = testContext.getEndDate().getTime();
				startTime = Math.min(startTime, contextStartTime);
				endTime = Math.max(endTime, contextEndTime);
			}
		}

		// Convert start time and end time to readable date-time format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedStartTime = dateFormat.format(new Date(startTime));
		String formattedEndTime = dateFormat.format(new Date(endTime));
		long totalExecutionTime = endTime - startTime;
		String formattedTotalExecutionTime = formatDuration(totalExecutionTime);
		htmlContent.append("<!DOCTYPE html>").append("<html>").append("<head>")
				.append("<title>AIA Automation Execution</title>")
				.append("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>").append("<style>")
				.append("/* Your CSS Styles */").append(".pass { background-color: #7FFF7F; }") // Green background for
																								// pass
				.append(".fail { background-color: #FF7F7F; }") // Red background for fail
				.append(".skip { background-color: #FFFF7F; }") // Yellow background for skipped
				.append("p.execution_stats {line-height: 1.2;}") // Adjust the margin-bottom for time info
				.append(".space-between {height: 20px;} ").append(".space-between-percentage {height: 3px;} ")
				.append("</style>").append("</head>").append("<body>").append("<P> Hello team,</P>")
				.append("<p>Initiated the test automation on the recent build and please see below for the quick summary of test results.</p>")
				.append("<p class='execution_stats'>").append("<u><b>Execution Summary:</b></u>").append("<br>")
				.append("Execution Start Time: ").append(formattedStartTime).append(" sec").append("<br>")
				.append("Execution End Time: ").append(formattedEndTime).append(" sec").append("<br>")
				.append("Total Execution Time(Including Re-run): ").append(formattedTotalExecutionTime).append(" sec")
				.append("<br>").append("Pass Percentage: ").append(passPercentage).append("%").append("</p>")
				.append(" <div class=space-between-percentage></div>");
//        .append("<div style='width: 50%; margin: auto;'>")
//        .append("<canvas id='testPieChart'></canvas>")
//        .append("</div>")

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult suiteResult : suiteResults.values()) {
				ITestContext testContext1 = suiteResult.getTestContext();
				for (ITestNGMethod method : testContext1.getAllTestMethods()) {
					IResultMap skippedResult = testContext1.getSkippedTests();
					passCount += testContext1.getPassedTests().size();
					failCount += testContext1.getFailedTests().size();
					skipCount += testContext1.getSkippedTests().size();
				}

				htmlContent.append("<table>").append("<tr align=center>").append(" <td colspan=4>")
						.append("<h2>" + executedModule.replaceAll("Suite.xml", "") + "</h2>").append(" </td></tr>")
						.append("<tr><td>").append("<table style=background:#67c2ef;width:120px>")
						.append(" <tr><td style=font-size: 36px class=value align=center>"
								+ testContext1.getAllTestMethods().length + "</td></tr>")
						.append("<tr><td align=center>Total</td></tr>").append("</table></td>").append("<td>")
						.append("<table style=background:#79c447;width:120px>")
						.append("<tr><td style=font-size: 36px class=value align=center>"
								+ testContext1.getPassedTests().size() + "</td></tr>")
						.append(" <tr><td align=center>Passed</td></tr>").append(" </table></td>").append(" <td>")
						.append("<table style=background:#ff5454;width:120px>")
						.append("<tr><td style=font-size: 36px class=value align=center>"
								+ testContext1.getFailedTests().size() + "</td></tr>")
						.append("<tr><td align=center>Failed</td></tr>").append(" </table></td>").append("<td>");
				if (testContext1.getSkippedTests().getAllMethods() != null) {
					htmlContent.append("<table style=background:#fabb3d;width:120px>")
							.append("<tr><td style=font-size: 36px class=value align=center>" + "0" + "</td></tr>")
							.append("<tr><td align=center>Skipped</td></tr>").append("</table></td>").append("<td>")
							.append("</tr></table>").append(" <div class=space-between></div>");
				} else {
					htmlContent.append("<table style=background:#fabb3d;width:120px>")
							.append("<tr><td style=font-size: 36px class=value align=center>"
									+ testContext1.getSkippedTests().size() + "</td></tr>")
							.append("<tr><td align=center>Skipped</td></tr>").append("</table></td>").append("<td>")
							.append("</tr></table>");

				}
				htmlContent.append(" <div class=space-between></div>");

				htmlContent
						.append("<table border='1' style='width: 50%; margin: auto; margin-top: 0; margin-left: 0;'>")
						.append("<tr><th>#</th><th>Test Case Description</th><th>Status</th><th>Execution Time (min:sec)</th><th>Retried Runs</th></tr>");

				int serialNumber = 1;
//        int passCount = 0;
//        int failCount = 0;
//        int skipCount = 0;
				for (ISuite suite1 : suites) {
					Map<String, ISuiteResult> suiteResults1 = suite1.getResults();
					for (ISuiteResult suiteResult1 : suiteResults1.values()) {
						ITestContext testContext11 = suiteResult1.getTestContext();
						for (ITestNGMethod method : testContext11.getAllTestMethods()) {
							IResultMap skippedResult = testContext11.getSkippedTests();
							passCount += testContext11.getPassedTests().size();
							failCount += testContext11.getFailedTests().size();
							skipCount += testContext11.getSkippedTests().size();
						}

				for (ITestNGMethod method : testContext11.getAllTestMethods()) {
				    int retries = 0; // Counter for retries
				    // Loop through the failed results to count retries
				    for (ITestResult failedResult : testContext11.getSkippedTests().getAllResults()) {
				        if (failedResult.getMethod().equals(method) && failedResult.wasRetried()) {
				            retries++;
				        }
				    }
				    for (ITestResult passedResult : testContext11.getPassedTests().getAllResults()) {
				        if (passedResult.getMethod().equals(method) && passedResult.wasRetried()) {
				            retries++;
				        }
				    }

				    IResultMap passedResult = testContext11.getPassedTests();
				    IResultMap failedResult = testContext11.getFailedTests();
				    IResultMap skippedResult = testContext11.getSkippedTests();

				    ITestResult result;
				    String status;

				    if ((result = getTestResultForMethod(method, passedResult)) != null) {
				        status = "Pass";
				    } else if ((result = getTestResultForMethod(method, failedResult)) != null) {
				        status = "Fail";
				    } else if ((result = getTestResultForMethod(method, skippedResult)) != null) {
				        status = "Skip";
				    } else {
				        // No result found
				        continue;
				    }

				    long executionTimeMillis = result.getEndMillis() - result.getStartMillis();
				String executionTimeSeconds=    formatDuration(executionTimeMillis);
//				    long executionTimeSeconds = executionTimeMillis / 1000;
				    String minutes = executionTimeSeconds.substring(3, 5);; 
				    String seconds = executionTimeSeconds.substring(6, 8);;

				    htmlContent.append("<tr class='").append(status.toLowerCase()).append("'>")
				            .append("<td>").append(serialNumber++).append("</td>")
				            .append("<td>").append(method.getDescription() != null ? method.getDescription() : method.getMethodName()).append("</td>")
				            .append("<td>").append(status).append("</td>")
				            .append("<td>").append(minutes).append(":").append(seconds).append("</td>")
				            .append("<td>").append(retries).append("</td>")
				            .append("</tr>");
				}
					}
				}

//	    int totalTests = passCount + failCount + skipCount;
//	    float passPercentage = ((float) passCount / totalTests) * 100;
//				float failPercentage = ((float) failCount / totalTests) * 100;
//				float skipPercentage = ((float) skipCount / totalTests) * 100;

				htmlContent.append("</table>")
//	            .append("<script>")
//	            .append("var ctx = document.getElementById('testPieChart').getContext('2d');")
//	            .append("var testPieChart = new Chart(ctx, {")
//	            .append("type: 'pie',")
//	            .append("data: {")
//	            .append("labels: ['Pass', 'Fail', 'Skip'],")
//	            .append("datasets: [{")
//	            .append("data: [").append(passPercentage).append(", ").append(failPercentage).append(", ").append(skipPercentage).append("],")
//	            .append("backgroundColor: ['#7FFF7F', '#FF7F7F', '#FFFF7F']") // Green for pass, Red for fail, Yellow for skip
//	            .append("}]")
//	            .append("},")
//	            .append("options: {")
//	            .append("title: { display: true, text: 'Pass/Fail/Skip Percentage' },")
//	            .append("responsive: true,")
//	            .append("maintainAspectRatio: false,")
//	            .append("}")
//	            .append("});")
//	            .append("</script>")
						.append("<p>Thank you..</p>").append("</body>").append("</html>");
			  saveReportToFile(htmlContent.toString(), System.getProperty("user.dir")+"/Reports" + "/testReport.html");
			}
		}
	}

	private ITestResult getTestResultForMethod(ITestNGMethod method, IResultMap testResult) {
		for (ITestResult result : testResult.getAllResults()) {
			if (method.equals(result.getMethod())) {
				return result;
			}
		}
		return null;
	}

	private void saveReportToFile(String content, String filePath) {
		try {
			FileWriter fileWriter = new FileWriter(filePath);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(content);
			bufferedWriter.close();
			fileWriter.close();
			System.out.println("HTML report saved successfully: " + filePath);
		} catch (IOException e) {
			System.err.println("Error occurred while saving the HTML report: " + e.getMessage());
		}
	}

	private String formatDuration(long duration) {
		long seconds = duration / 1000;
		long minutes = seconds / 60;
		seconds = seconds % 60;
		long hours = minutes / 60;
		minutes = minutes % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

}
