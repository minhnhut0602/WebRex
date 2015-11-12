/*
    Copyright [2015-2016] eBay Software Foundation

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.ebayopensource.webrex.util;

import java.util.ArrayList;
import java.util.List;

public class Splitters {
   public static StringSplitter by(char delimiter) {
      return new StringSplitter(delimiter);
   }

   public static StringSplitter by(String delimiter) {
      return new StringSplitter(delimiter);
   }

   public static class StringSplitter {
      private char m_charDelimiter;

      private String m_stringDelimiter;

      private boolean m_trimmed;

      private boolean m_noEmptyItem;

      StringSplitter(char delimiter) {
         m_charDelimiter = delimiter;
      }

      StringSplitter(String delimiter) {
         m_stringDelimiter = delimiter;
      }

      protected List<String> doCharSplit(String str, List<String> list) {
         char delimiter = m_charDelimiter;
         int len = str.length();
         StringBuilder sb = new StringBuilder(len);

         for (int i = 0; i < len + 1; i++) {
            char ch = i == len ? delimiter : str.charAt(i);

            if (ch == delimiter) {
               String item = sb.toString();

               sb.setLength(0);

               if (m_trimmed) {
                  item = item.trim();
               }

               if (m_noEmptyItem && item.length() == 0) {
                  continue;
               }

               list.add(item);
            } else {
               sb.append(ch);
            }
         }

         return list;
      }

      protected List<String> doStringSplit(String source, List<String> list) {
         String delimiter = m_stringDelimiter;
         int len = delimiter.length();
         int offset = 0;
         int index = source.indexOf(delimiter, offset);

         while (true) {
            String part;

            if (index == -1) { // last part
               part = source.substring(offset);
            } else {
               part = source.substring(offset, index);
            }

            if (m_trimmed) {
               part = part.trim();
            }

            if (!m_noEmptyItem || part.length() > 0) {
               list.add(part);
            }

            if (index == -1) { // last part
               break;
            } else {
               offset = index + len;
               index = source.indexOf(delimiter, offset);
            }
         }

         return list;
      }

      public StringSplitter noEmptyItem() {
         m_noEmptyItem = true;
         return this;
      }

      public List<String> split(String str) {
         return split(str, new ArrayList<String>());
      }

      public List<String> split(String str, List<String> list) {
         if (str == null) {
            return null;
         }

         if (m_charDelimiter > 0) {
            return doCharSplit(str, list);
         } else if (m_stringDelimiter != null) {
            return doStringSplit(str, list);
         }

         throw new UnsupportedOperationException();
      }

      public StringSplitter trim() {
         m_trimmed = true;
         return this;
      }
   }

   /**
    * Return the count of the char in the String
    * if src == null return -1;
    * if src.length() == 0 return 0;
    */
   private static int countChar(String src, int length, char c) {
      int count = 0;
      int ch;
      for (int i = 0; i < length; i++) {
         ch = src.charAt(i);
         if (ch == c) {
            count++;
         }
      }
      return count;
   }

   public static String[] split(String src, char sep) {
      if (src == null) {
         return new String[]{};
      }
      int length = src.length();
      if (length == 0) {
         return new String[] {};
      }

      int count = countChar(src, length, sep);
      String[] array = new String[count + 1];
      int begin = 0;
      int end;
      for (int i = 0; i <= count; i++) {
         end = src.indexOf(sep, begin);
         if (end == -1) {
            array[i] = begin == length ? "" : src.substring(begin);
            break;
         } else {
            array[i] = src.substring(begin, end);
            begin = end + 1;
         }
      }
      return array;
   }
}
